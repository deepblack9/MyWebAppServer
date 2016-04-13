package com.bruce.framework.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bruce.framework.exception.BaseUncheckedException;

/**
 * 基础框架的数据访问层抽象接口类(整合Hibernate和iBatis两种orm框架)，所有模块的数据访问层接口均继承该接口。<br>
 * <p/> 该基础框架接口把Hibernate及iBatis两种orm组合在一起。把两种orm框架整合在一起的理由：<br>
 * <p/> 1）Hibernate比较适合对单实例进行增、删、改、查的操作。<br>
 * 2）iBatis比较适合进行复杂表查询、批量操作(或只更新部分字段的单表)、存储过程等复杂的操作，方便利用DB底层的功能，<br>
 * 同时可最大化利用DB本身的操作（与具体DB有关）。<br>
 * 3）保证两种DAO中事务的一致性(配置&lt;property name="useTransactionAwareDataSource"
 * value="true"&gt;&lt;/property&gt;) <br>
 * 4）以组合方式，将HibernateTemplate和sqlMapClientTemplate集成起来，<br>
 * 完成类似SqlMapClientDaoSupport和HibernateDaoSupport的工作，解决Java无法多类继承的情况。<br>
 * <p/> 目前该DAO框架只支持一个DAO只能同时操作一个Hibernate
 * SessionFactory的一个DataSource或一个iBatis的DataSource。<br>
 * 如果要实现在一个功能里操作不同的DataSource，可以分多个DAO实现，每个DAO配置一个DataSource，然后通过业务逻辑层Service来调用多个DAO。
 * <p/>
 * 该类实现Hiberante常用方法有（如果在具体Dao实现不够用的话，可用getHibernateTemplate().method()方式来取）：<br>
 * get,save,saveOrUpdate,update,delete, query, queryAll. <p/>
 * 该类实现iBatis常用方法有（如果在具体Dao实现不够用的话，可用getSqlMapClientTemplate.method()方式来取）：<br>
 * insertBySql,deleteBySql,updateBySql,queryListBySql,queryObjectBySql.
 * 
 * @author Aladding
 */
public interface IBaseDAO {
	public static String BEAN_ID = "baseDAO";
	// =================================以下代码为Hibernate实现的常用方法===========================//

	/**
	 * 根据主键加载特定持久化类的实例。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param id
	 *            主键
	 * @return Object 取得的实例
	 */
	public <T> T get(Class<T> entityClass, Serializable id);

	/**
	 * 根据主键加载特定持久化类的实例。 <p/> load与get区别：<br>
	 * <br>
	 * 1)load只会在hibernate的内部缓存和二级缓存中的查找实例，而get方法仅在内部缓存中查找，<br>
	 * 如果没有发现对应的数据，将越过二级缓存，直接调用SQL完成数据读取。<br>
	 * <p/> 2)如果对象不存在，get返回null，而load方法抛出异常，因此在使用load方法时，要确认查询的主键id一定是存在的。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param id
	 *            主键
	 * @return Object 取得的实例
	 */
	public <T> T load(Class<T> entityClass, Serializable id) ;

	/**
	 * 保存新的实例至持久层。
	 * 
	 * @param entity
	 *            要保存的新实例
	 */
	public void save(Object entity) ;

	/**
	 * 保存新的实例至持久层并立即刷新
	 * 
	 * @param entity
	 *            要保存的新实例
	 */
	public void saveAndFlush(Object entity) ;

	
	/**
	 * 根据实例状态，选择保存或者更新实例至持久层。
	 * 
	 * @param entity
	 *            要保存的实例
	 */
	public void saveOrUpdate(Object entity) ;
	
	/**
	 * 根据实例状态，选择保存或者更新实例至持久层，并立即刷新缓存
	 * 
	 * @param entity
	 *            要保存的实例
	 */
	public void saveOrUpdateAndFlush(Object entity) ;

	/**
	 * 更新现有实例至持久层。
	 * 
	 * @param entity
	 *            要更新的实例
	 */
	public void update(Object entity) ;

	/**
	 * 删除现有实例，同步持久层。
	 * 
	 * @param entity
	 *            要删除的实例
	 */
	public void delete(Object entity) ;

	/**
	 * 删除现有实例，同步持久层，并立即刷新缓存
	 * 
	 * @param entity
	 *            要删除的实例
	 */
	public void deleteAndFlush(Object entity) ;

	
	/**
	 * 根据主键删除现有持久化类的实例，同步持久层。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param id
	 *            主键
	 */
	public void delete(Class<?> entityClass, Serializable id) ;
	
	/**
	 * 根据集合保存的id删除现有持久化类的实例，同步持久层。
	 * 
	 * @param entityClass
	 *            持久化类
	 * @param id
	 *            主键
	 *            
	 *  Aladding 2010-1-4 增加
	 */
	public void delete(Class<?> entityClass, Collection<Serializable> ids) ;

	/**
	 * 删除集合内全部持久化类实例，同步持久层。
	 * 
	 * @param entities
	 *            要删除的实例集合
	 */
	public void deleteAll(Collection<?> entities) ;

	/**
	 * 根据条件使用HQL语句查询数据。<br>
	 * <p/> 具有功能：<br>
	 * 1）支持查询分页，该方法会利用数据库本身的分页技术实现。说明如下：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;a)如果数据库(如MySQL,Oracle,SQL Server2005等)支持limit
	 * n,m，查询效率最高；<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;b)如果数据库(如informix,Sybase 12.5.3,SQL Server等)支持top
	 * n，查询效率次之（查询结果越大，效率越低）； <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;c)如果以上两种均不支持，查询效率最低。<br>
	 * 2）支持查询总记录数<br>
	 * 3）支持order by，group by,having等 <br>
	 * <p/> 不支持功能：<br>
	 * 1）不支持select里的嵌套子查询。如不允许这种用法：select a,b,(select c from table1) as d from
	 * table2 ...<br>
	 * 2）条件与条件之间不支持or，而是用and。<br>
	 * <p/> 示例如下：<br>
	 * 
	 * <pre>
	 * 1)查询所有用户信息:
	 * &lt;p/&gt;
	 * xxxDao.query(&quot;from TUser&quot;,null,null,null,0,0);
	 * &lt;p/&gt;
	 * 2)查询用户名含有&quot;admin&quot;开头，注册日期2006-12-01之前前10条用户信息(用户名及注册日期):
	 * &lt;p/&gt;
	 * xxxDao.query(&quot;select userName,createDate from TUser&quot;,
	 *               new String[]{&quot;userName&quot;,&quot;createDate&quot;},
	 *               new String[]{&quot;like&quot;,&quot;&gt;=&quot;},
	 *               new Object[]{&quot;admin%&quot;,&quot;2006-12-01&quot;},0,10);
	 * &lt;p/&gt;
	 * &lt;b&gt;注意：对于多对象关联查询，必须指定返回的对象类型。&lt;/b&gt;
	 * <br>
	 * 示例如下：
	 * &lt;pre&gt;
	 * &lt;b&gt;1. 插寻结果bean，A的属性a、b来源于已影射的bean M,N&lt;/b&gt;
	 * class A{
	 *      String a,b;
	 *      public A(String a,String b){
	 *          this.a=a;
	 *          thia.b=b;
	 *      }
	 * }
	 * &lt;p/&gt;
	 * &lt;b&gt;2.编写查询方法&lt;/b&gt;
	 * Query q= session.createQuery(&quot;select new A(M.a,N.b) from M as M,N as N where M.id=N.id&quot;);
	 * &lt;/pre&gt;
	 * 
	 * @param hql           HQL查询语句（不带Where条件）。不允许在select段内使用子查询，如不允许这种用法：
	 *                      select a,b,(select c from table1) as d from table2 ...
	 *                      1)对于查询全部对象属性，(&lt;b&gt;select *&lt;/b&gt;)不可写。如：from TUser；
	 *                      2)对于查询部分对象属性，则必须写完整，如：select userName,password from TUser;
	 * @param propertyNames 查询条件的属性名列表
	 * @param operators     查询条件的操作符列表，如果查询条件中存在不为&lt;b&gt;=&lt;/b&gt;的操作符，需要填写该列表，否则为null，
	 *                      应与属性名列表一一对应。操作符包括=, &gt;=, &lt;=, &lt;&gt;, !=, like。
	 * @param values        查询条件的值列表，该列表应当与属性列表一一对应
	 * @param offset        查询结果的起始行，从0开始。如果不需要，则设置为-1。
	 * @param size          查询结果的最大行数。如果不需要，则设置为-1
	 * @param isTotalSize   是否需要返回本次查询的总记录数，默认false
	 * @param orderBy       排序字符串,不含order by字符串，如orderBy=&quot;a desc,b asc&quot;,最后生成为：order by a desc,b asc
	 * @param groupBy       分组字符串,不含group by 字符串，如groupBy=&quot;a desc,b asc&quot;,最后生成为：group by a desc,b asc
	 * @param otherCause    where后面的其它语句，如排序(order by),分组(group by)及聚集(having)。
	 *                      如&quot;group by name order by name desc&quot;
	 * @return Object[]    有两个值，第一个值表示查询结果列表List list = (List)Object[0]
	 *         第二个表示查询返回的总记录数，int count = ((Integer)Object[1]).intValue;
	 * @throws com.bruce.framework.exception.BaseUncheckedException
	 *          基础不可控异常类
	 * 
	 */
	public Object[] query(final String hql, final String[] propertyNames,
			final String[] operators, final Object[] values, final int offset,
			final int size, final boolean isTotalSize, final String orderBy,
			final String groupBy, final String otherCause)
			throws BaseUncheckedException;

	/**
	 * 根据条件使用HQL语句查询，返回符合条件指定范围的数据，HQL语句不带where 条件，仅为select ... from ...部分）。<br>
	 * 
	 * @see com.bruce.framework.base.dao.telstar.framework.base.dao.IBaseDAO.IBaseDAO#query(String,String[],String[],Object[],int,int,boolean,String,String,String)
	 */
	public Object[] queryPageComplex(final String hql,
			final String[] propertyNames, final String[] operators,
			final Object[] values, final int offset, final int size,
			final boolean isTotalSize) throws BaseUncheckedException;

	/**
	 * 根据条件使用HQL语句查询数据，返回符合条件指定范围的list，HQL语句不带where 条件，仅为select ... from ...部分）。<br>
	 * 
	 * @see com.bruce.framework.base.dao.telstar.framework.base.dao.IBaseDAO.IBaseDAO#query(String,String[],String[],Object[],int,int,boolean,String,String,String)
	 */
	public <T> List<T> queryPageList(final String hql,
			final String[] propertyNames, final String[] operators,
			final Object[] values, final int offset, final int size)
			throws BaseUncheckedException;

	/**
	 * 根据条件使用HQL语句查询，返回符合条件指定范围的数据，HQL语句不带where 条件，仅为select ... from ...部分）。<br>
	 * 
	 * @see com.bruce.framework.base.dao.telstar.framework.base.dao.IBaseDAO.IBaseDAO#query(String,String[],String[],Object[],int,int,boolean,String,String,String)
	 */
	public Object[] queryPageComplex(final String hql, final int offset,
			final int size, final boolean isTotalSize)
			throws BaseUncheckedException;

	/**
	 * 根据HQL语句查询数据，返回符合条件指定范围的list。HQL可为完整语句。<br>
	 * 如select ... from ... where ... order by ... group by ...。<br>
	 * 
	 * @see com.bruce.framework.base.dao.telstar.framework.base.dao.IBaseDAO.IBaseDAO#query(String,String[],String[],Object[],int,int,boolean,String,String,String)
	 */
	public <T> List<T> queryPageList(final String hql, final int offset,
			final int size) throws BaseUncheckedException;

	/**
	 * 根据条件使用HQL语句查询，返回所有符合条件的list(可用list.size()得到返回的总记录数)。<br>
	 * HQL语句不带where 条件，仅为select ... from ...部分。<br>
	 * 
	 * @see com.bruce.framework.base.dao.telstar.framework.base.dao.IBaseDAO.IBaseDAO#query(String,String[],String[],Object[],int,int,boolean,String,String,String)
	 */
	public <T> List<T> queryAll(final String hql,
			final String[] propertyNames, final String[] operators,
			final Object[] values) throws BaseUncheckedException;

	/**
	 * 根据HQL语句查询，返回所有符合条件的list(可用list.size()得到返回的总记录数)。<br>
	 * HQL可为完整语句。如select ... from ... where ... order by ... group by ...。<br>
	 * 
	 * @see com.bruce.framework.base.dao.telstar.framework.base.dao.IBaseDAO.IBaseDAO#query(String,String[],String[],Object[],int,int,boolean,String,String,String)
	 */
	public <T> List<T> queryAll(final String hql) throws BaseUncheckedException;

	/**
	 * 根据hsql批量更新(删除／修改)。 <p/> 示例如下：
	 * 
	 * <pre>
	 * 1)批量删除
	 * String hql = &quot;delete Student s where s.sage&gt;25&quot;;
	 * int intCount = this.batchUpdate(hql);
	 * 2)批量更新
	 * String hql = &quot;update Student s set s.sage='22' where s.id=11&quot;
	 *  int intCount = this.batchUpdate(hql);
	 * </pre>
	 * 
	 * @param hql
	 *            HQL更新及删除语句
	 * @return 更新或删除成功的记录数
	 */
	public int batchUpdate(String hql) throws BaseUncheckedException;
	
	/**
	 * 执行删除或修改HQL
	 * 
	 * @param hql HQL语句
	 * @param parameter 需要绑定的命名参数,key值与HQL命名参数对应
	 * @return
	 * @throws BaseUncheckedException
	 * 
	 * @author Aladding
	 * 2010-01-24
	 */
	public int executeUpdate(String hql ,Map<String,?> parameter) throws BaseUncheckedException;

	// =================================以下代码为iBatis实现的常用方法(后缀加BySql)===========================//

	/**
	 * 往库表中插入记录（可只插入记录的部分字段，不同于hibernate的save要保存整个实例的所有属性到DB），返回插入后的对象。 <p/>
	 * 示例如下：<br>
	 * 
	 * <pre>
	 * 一、定义sqlMap文件
	 * &lt;p/&gt;
	 * &lt;sqlMap namespace=&quot;user&quot;&gt;
	 *   &lt;resultMap id=&quot;User&quot;
	 *     &lt;result column=&quot;user_name&quot; property=&quot;username&quot; jdbcType=&quot;CHAR&quot; /&gt;
	 *     &lt;result column=&quot;password&quot; property=&quot;password&quot; jdbcType=&quot;CHAR&quot; /&gt;
	 *   &lt;/resultMap&gt;
	 * &lt;p/&gt;
	 *   &lt;insert id=&quot;insert&quot; parameterClass=&quot;User&quot;&gt;
	 *     insert into user (user_name,password)
	 *     values (#username#,#password#)
	 *   &lt;/insert&gt;
	 * &lt;/sqlMap&gt;
	 * &lt;p/&gt;
	 * 二、调用方法处理
	 * &lt;p/&gt;
	 * this.insert(&quot;user.insert&quot;,user);
	 * </pre>
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param value
	 *            要操作的对象
	 * @return 插入后的对象
	 */
	public <T> T insertBySql(String statementId, Object value);

	/**
	 * 删除库表中的记录（可批量删除），返回删除成功的记录数。 <p/> 示例如下：<br>
	 * 
	 * <pre>
	 * 一、定义sqlMap文件
	 * &lt;p/&gt;
	 * &lt;sqlMap namespace=&quot;user&quot;&gt;
	 *   &lt;delete id=&quot;delete&quot; parameterClass=&quot;String&quot;&gt;
	 *     delete from user where user_name = #value#
	 *   &lt;/delete&gt;
	 * &lt;/sqlMap&gt;
	 * &lt;p/&gt;
	 * 二、调用方法处理
	 * &lt;p/&gt;
	 * int count = this.deleteBySql(&quot;user.delete&quot;,&quot;admin&quot;);
	 * </pre>
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param value
	 *            删除条件值
	 * @return 删除成功的记录数
	 */
	public int deleteBySql(String statementId, Object value);

	/**
	 * 更新库表中的记录（可批量更新），返回更新成功的记录数。 <p/> 示例如下：<br>
	 * 
	 * <pre>
	 * 一、定义sqlMap文件
	 * &lt;p/&gt;
	 * &lt;sqlMap namespace=&quot;user&quot;&gt;
	 *   &lt;update id=&quot;update&quot; parameterClass=&quot;java.util.HashMap&quot;&gt;
	 *     update  user set user_name = #new_user_name#
	 *     where user_name = #old_user_name#
	 *   &lt;/update&gt;
	 * &lt;/sqlMap&gt;
	 * &lt;p/&gt;
	 * 二、调用方法处理
	 * &lt;p/&gt;
	 *  Map map = new HashMap();
	 * map.put(&quot;new_user_name&quot;,&quot;admin2&quot;);
	 * map.put(&quot;old_user_name&quot;,&quot;admin&quot;);
	 * int count = this.updateBySql(&quot;user.update&quot;,map);
	 * </pre>
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param value
	 *            更新条件值值
	 * @return 更新成功的记录数
	 */
	public int updateBySql(String statementId, Object value);

	/**
	 * 查询符合条件的记录，生成List返回。 举两个示例，一个是普通查询记录，返回元素是HashMap的List；另一个是执行存储过程 <p/>
	 * 
	 * <pre>
	 * 1、普通查询记录：
	 * &lt;p/&gt;
	 * 一、定义sqlMap文件
	 * &lt;p/&gt;
	 * &lt;sqlMap namespace=&quot;user&quot;&gt;
	 *   &lt;select id=&quot;selectUser&quot; parameterClass=&quot;int&quot; resultClass=&quot;java.util.HashMap&quot;&gt;
	 *      select user_name,password from user where id &gt; #value#
	 *   &lt;/select&gt;
	 * &lt;/sqlMap&gt;
	 * &lt;p/&gt;
	 * 二、调用方法处理
	 * &lt;p/&gt;
	 *   List list = this.queryListBySql(user.selectUser&quot;,new Integer(10));
	 * &lt;p/&gt;
	 * 2.存储过程
	 * &lt;p/&gt;
	 * 一、定义sqlMap文件
	 * &lt;p/&gt;
	 * &lt;sqlMap namespace=&quot;user&quot;&gt;
	 *   &lt;parameterMap id=&quot;procParamMap&quot; class=&quot;java.util.HashMap&quot; &gt;
	 *     &lt;parameter property=&quot;id&quot; jdbcType=&quot;INTEGER&quot; javaType=&quot;java.lang.Integer&quot; mode=&quot;IN&quot;/&gt;
	 *     &lt;parameter property=&quot;outid&quot; jdbcType=&quot;INTEGER&quot; javaType=&quot;java.lang.Integer&quot; mode=&quot;OUT&quot;/&gt;
	 *     &lt;parameter property=&quot;errMsg&quot; jdbcType=&quot;VARCHAR&quot; javaType=&quot;java.lang.String&quot; mode=&quot;OUT&quot;/&gt;
	 *   &lt;/parameterMap&gt;
	 * &lt;p/&gt;
	 *   &lt;resultMap id=&quot;procResultMap&quot; class=&quot;java.util.HashMap&quot; &gt;
	 *     &lt;result property=&quot;a&quot; column=&quot;AAA&quot;/&gt;
	 *     &lt;result property=&quot;b&quot; column=&quot;BBB&quot;/&gt;
	 *     &lt;result property=&quot;c&quot; column=&quot;CCC&quot;/&gt;
	 *   &lt;/resultMap&gt;
	 * &lt;p/&gt;
	 * &lt;procedure id=&quot;procTest&quot; parameterMap=&quot;procParamMap&quot; resultMap=&quot;procResultMap&quot;&gt;
	 *  {call test_sp_1 (?,?,?)}
	 * &lt;/procedure&gt;
	 * &lt;/sqlMap&gt;
	 * &lt;p/&gt;
	 * 二、书写存储过程
	 * &lt;p/&gt;
	 * CREATE procedure test_sp_1 @id int,@outid int output,@errMsg varchar(255) output
	 * AS
	 * update ACCOUNT set pid=@id
	 * select @outid=isnull(@id,0)+1
	 * select @errMsg='测试'
	 * select AAA='AAAAAAA',BBB='BBBBBBB',CCC='CCCCCCC'
	 * GO
	 * &lt;p/&gt;
	 * 三、调用方法
	 * &lt;p/&gt;
	 * Map map = new HashMap();
	 * //为map赋值
	 * ...
	 *  List ret = (List) this.queryListBySql(&quot;user.procTest&quot;,map);
	 * </pre>
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param value
	 *            查询条件值
	 * @return list 找到的记录
	 */
	public <T> List<T> queryListBySql(String statementId, Object value);

	/**
	 * 查询符合条件的记录，并从指定位置开始返回指定数目的结果集(可用于分页查询)。<br>
	 * <p/> <b>特别说明：由于该命令采用的分页技术不是数据库本身的分页技术，而是采用ResultSet的absolute定位技术，<br>
	 * 需要把查询结果全部装入ResultSet再定位。如果查询结果较大（1万条记录以上)，效率会很低。<br>
	 * 建议使用Hibernate的query方法或在SQL Map的XML中使用数据库内部分页技术<br>
	 * （即把offset,size作为参数传入SQL语句的类似limit n,m中）来查询。 </b> <p/>
	 * 不能从这个方法得到查询结果集的总数，但这个总数可以再执行一个简单的语句count()来得到。
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param value
	 *            查询条件值
	 * @param offset
	 *            返回查询结果的起始行，从0开始
	 * @param size
	 *            返回查询结果的最大行数
	 * @return list 找到的记录
	 */
	public <T> List<T> queryPageListBySql(String statementId, Object value,
			int offset, int size);

	/**
	 * 查询符合条件的记录，生成Object返回。 <p/> 示例如下：<br>
	 * 
	 * <pre>
	 * 一、定义sqlMap文件
	 * &lt;p/&gt;
	 * &lt;sqlMap namespace=&quot;user&quot;&gt;
	 *   &lt;resultMap id=&quot;AccountResult&quot; class=&quot;Account&quot; groupBy=&quot;id&quot;&gt;
	 *     &lt;result property=&quot;id&quot; column=&quot;ID&quot;/&gt;
	 *     &lt;result property=&quot;firstName&quot; column=&quot;FIRST_NAME&quot;/&gt;
	 *     &lt;result property=&quot;lastName&quot; column=&quot;LAST_NAME&quot;/&gt;
	 *     &lt;result property=&quot;emailAddress&quot; column=&quot;EMAIL&quot;/&gt;
	 *     &lt;result property=&quot;role&quot; resultMap=&quot;Account.roleResult&quot;/&gt;
	 *   &lt;/resultMap&gt;
	 *   &lt;select id=&quot;selectAccountById&quot; parameterClass=&quot;int&quot; resultMap=&quot;AccountResult&quot;&gt;
	 *     select B.ROLEID, B.ROLENAME, A.ID, A.FIRST_NAME,A.LAST_NAME,A.EMAIL from ACCOUNT A
	 *     left outer join ROLE B on A.ROLEID = B.ROLEID where A.ID = #id#
	 *   &lt;/select&gt;
	 * &lt;/sqlMap&gt;
	 * &lt;p/&gt;
	 * 二、调用方法处理
	 * &lt;p/&gt;
	 * Account account = (Account)this.queryObjectBySql(&quot;user.selectAccountById&quot;,new Integer(10));
	 * </pre>
	 * 
	 * @param statementId
	 *            调用iBatis的SqlMap文件的声明段名，规则名：SqlMap的namespace+"." +
	 *            该sqlMap文件某片段的id
	 * @param value
	 *            查询条件值
	 * @return list 找到的记录
	 */
	public <T> T queryObjectBySql(String statementId, Object value);
}
