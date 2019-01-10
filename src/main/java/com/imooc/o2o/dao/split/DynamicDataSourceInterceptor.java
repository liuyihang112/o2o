package com.imooc.o2o.dao.split;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Dynamic;

/**
 * mybatis层面的拦截器，去拦截mybatis传递进来的sql信息。然后根据sql信息的不同采取不同的数据源 update insert delete
 * ---采用master query select -----采用slave
 * 
 * @author lyh 19.1.9
 *
 */
//mybatis会把对数据的增删改封装到update method中。所以不需要有delete insert.只需要有update 和 query 两个method就可以代表数据库的增删改查操作了
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
//query方法中args后面的两个参数是因为query需要返回数据，因此需要这些参数
public class DynamicDataSourceInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
	// insert update delete 做数据更新的都采用主库
	private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 判断传进来的sql信息是否是事务性的,就是@Transactional
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		// 将拦截的sql信息转化为Objects数组。
		Object[] objects = invocation.getArgs();
		MappedStatement ms = (MappedStatement) objects[0];
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		if (synchronizationActive != true) {
			// 读方法,虽然是读方法，按道理说应该用从库，但是，由于在mybatis中配置了useGenarateKey的方法，也就自动主键加一。而这个方法需要在执行时调用(SELECT
			// LAST INSERT_ID())方法。因此也需要使用主库
			if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// selectKey为自增id查询主键(SELECT LAST INSERT_ID())方法，使用主库
				if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", "");
					if (sql.matches(REGEX)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}
		} else {
			lookupKey = DynamicDataSourceHolder.DB_MASTER;
		}
		logger.debug("设置方法[{}] use[{}] Strategy, SqlCommanType[{}]..", ms.getId(), lookupKey,
				ms.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	/**
	 * plugin方法：其实就是决定返回的是传入的对象本体还是经过拦截器拦截后的做包装生成的代理对象
	 * Executor：为什么要做类型判断呢，是因为在mybatis的Executor中。包含了mybatis的增删改查操作，因此，只要你的操作是属于
	 * mybatis的增删改查，都能拦截的到。然后去做拦截处理
	 */
	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
