## 使用安装
git clone 当前项目，然后进入jsql目录下的jsql项目执行mvn install 安装
进入jsql-spring-boot-starter 执行mvn install安装
进入用idea或eclipse打开jsql-generator项目，找到GeneratorCode 修改生成配置，生成代码
假设有一下4张表

```sql
CREATE TABLE `tb_book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '书籍名称',
  `description` varchar(500) DEFAULT NULL COMMENT '书籍描述',
  `reader_id` bigint DEFAULT NULL COMMENT '读者名称',
  PRIMARY KEY (`id`)
)

CREATE TABLE `tb_catalog` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(30) DEFAULT NULL COMMENT '分类名称',
  `parent_id` bigint DEFAULT NULL COMMENT '上级分类id',
  PRIMARY KEY (`id`)
)

CREATE TABLE `tb_book_catalog` (
  `book_id` bigint NOT NULL COMMENT '书籍id',
  `catalog_id` bigint NOT NULL COMMENT '分类id',
  PRIMARY KEY (`book_id`,`catalog_id`)
)

CREATE TABLE `tb_reader` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '读者姓名',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
)
```
则生成代码的配置为：
```java
public class GeneratorCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSqlGenerator jsqlGenerator = new JSqlGenerator(
				"jdbc:mysql://localhost:3306/library?serverTimezone=GMT%2B8&useSSL=false&verifyServerCertificate=true",
				"root",
				"123456",
				"D:\\workspace\\jsql-test\\library\\");

		//表快速引用类包路径
		jsqlGenerator.setTPackage("com.example.library.t");

		//tb_book表代码配置
		GeneratorConfig.Builder builder = GeneratorConfig.createBuilder();
		//表配置，配置表前缀后生成代码时会将前缀去除
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_book","tb_"));
		//entity生成配置
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		//dto生成配置
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		//basedao生成配置
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		//dao生成配置
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		//生成
		jsqlGenerator.add(builder.build());
		//tb_catalog表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_catalog","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		//tb_book_catalog表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_book_catalog","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		//tb_reader表代码配置
		builder = GeneratorConfig.createBuilder();
		builder.setTableConfig(new TableConfig("com.example.library.table","tb_reader","tb_"));
		builder.setEntityConfig(new EntityConfig("com.example.library.entity"));
		builder.setDtoConfig(new DTOConfig("com.example.library.dto"));
		builder.setBaseDaoConfig(new BaseDaoConfig("com.example.library.basedao"));
		builder.setDaoConfig(new DaoConfig("com.example.library.dao"));
		jsqlGenerator.add(builder.build());

		jsqlGenerator.add(builder.build());
		jsqlGenerator.generate();

		System.out.println("生成成功!");
	}

}

```
生成后的代码目录为:
basedao 生成每张表的dao的基类，包含基本的增删改查如BookBaseDao类,此类不用修改，如遇数据库改动可直接替换此类

```java
//为了简化方法的实现已删除
public class BookBaseDao{
	@Autowired
	SqlExecutor sqlExecutor;
	public int insertBook(Book param);
	public int updateBook(Book param);
	public int deleteBookById(Long id);
	public List<Book> selectBookList(Book book);
	public Book selectBookById(Long id);
	public FetchConditionBuilder<Book> selectFromBook();
}
```
dao包下面为继承BaseDao的类

```java
@Repository
public class BookDao extends BookBaseDao{
	//用户自定义的sql语句可以写在这里	
}

```
com.example.library.t.T，方便写sql语句时的快速引用类

## 生成代码的增删改查
基本的增删改查就不写了，写下非常实用的单表查询selectFromXxxx()方法
已生成的BookDao为例

```java
List<Book> bookList = bookDao.selectFromBook().where(BOOK.ID.gt(1)).fetchList();
```
对应打印的sql语句为

```sql
sql    >== select  BOOK.*  from tb_book BOOK where  BOOK.ID >  ?
params >== 1
rawsql >== select  BOOK.*  from tb_book BOOK where  BOOK.ID >  1
count  <== 2
```
分页写法

```java
Record record = bookDao.selectFromBook().where(BOOK.DESCRIPTION.like("'%测试%'")).and(BOOK.ID.gt(0)).fetchPage(0,10);
//总条数
long total = record.getTotal();
//分页数据
List<Book> bookList = record.getList(Book.class);
```
对应打印的调试sql语句

```sql
sql    >== select count(*) from tb_book BOOK where  BOOK.DESCRIPTION like  '%测试%'   and  BOOK.ID >  ?  
params >== 0
rawsql >== select count(*) from tb_book BOOK where  BOOK.DESCRIPTION like  '%测试%'   and  BOOK.ID >  0  
count  <== 2
sql    >== select  BOOK.*  from tb_book BOOK where  BOOK.DESCRIPTION like  '%测试%'   and  BOOK.ID >  ?   limit ? , ? 
params >== 0,0,10
rawsql >== select  BOOK.*  from tb_book BOOK where  BOOK.DESCRIPTION like  '%测试%'   and  BOOK.ID >  0   limit 0 , 10 
count  <== 2

```
## 使用SqlExecutor编写更灵活的sql
### 单表查询

```java
//查询符合条件的列表
Sql sql = select(BOOK.ALL).from(BOOK).where(BOOK.ID.gt(0)).and(BOOK.DESCRIPTION.like("'%测试%'"));
List<Book> bookList = sqlExecutor.selectList(sql,Book.class);
//取第一条数据
sql = select(BOOK.ALL).from(BOOK).
	       where(BOOK.ID.gt(0)).
					   and(BOOK.DESCRIPTION.like("'%测试%'")).
					   orderBy(Order.asc(BOOK.NAME),Order.desc(BOOK.DESCRIPTION)).
					   limit(1);
Book book = sqlExecutor.selectOne(sql,Book.class);
```
打印的sql语句

```sql
sql    >== select  BOOK.*  from tb_book BOOK where  BOOK.ID >  ?   and  BOOK.DESCRIPTION like  '%测试%'  
params >== 0
rawsql >== select  BOOK.*  from tb_book BOOK where  BOOK.ID >  0   and  BOOK.DESCRIPTION like  '%测试%'  
count  <== 2


sql    >== select  BOOK.*  from tb_book BOOK where  BOOK.ID >  ?   and  BOOK.DESCRIPTION like  '%测试%'   order by  BOOK.NAME asc, BOOK.DESCRIPTION desc  limit 1
params >== 0
rawsql >== select  BOOK.*  from tb_book BOOK where  BOOK.ID >  0   and  BOOK.DESCRIPTION like  '%测试%'   order by  BOOK.NAME asc, BOOK.DESCRIPTION desc  limit 1
count  <== 1
```


### 一对一的多表查询
tb_book表中有reader_id跟tb_reader表中的id时逻辑上的外键关联关系，jsql不强制要求必须建立外键关联关系
```java
Sql sql = select(BOOK.ALL,READER.ALL).from(BOOK,READER).where(BOOK.READER_ID.eq(READER.ID)).and(BOOK.ID.gt(0));
//left join 的多表关联写法
Sql sql = select(BOOK.ALL,READER.ALL).from(BOOK).leftJoin(READER).on(BOOK.READER_ID.eq(READER.ID)).where(BOOK.ID.gt(0));
Record record = sqlExecutor.selectList(sql);
//取出Book列表
List<Book> bookList = record.getList(Book.class);
//取出Reader列表
List<Reader> readerList = record.getList(Reader.class);
//将bookList转化为BookDTO List,BookDTO当中有reader属性
List<BookDTO> bookDTOList = BeanUtils.copyList(bookList, BookDTO.class);
//合并，将readerList中的Reader，根据readerId和Reader中的属性进行比较赋值
BeanUtils.merge(bookDTOList,readerList,"readerId","id","reader");
//如果BookDTO当中只有一个Reader类型的属性，则属性名可以省略掉
BeanUtils.merge(bookDTOList,readerList,"readerId","id");
```
打印的sql

```sql
sql    >== select  BOOK.* , READER.*  from tb_book BOOK, tb_reader READER where  BOOK.READER_ID =  READER.ID  and  BOOK.ID >  ?  
params >== 0
rawsql >== select  BOOK.* , READER.*  from tb_book BOOK, tb_reader READER where  BOOK.READER_ID =  READER.ID  and  BOOK.ID >  0  
//left join写法的sql语句
sql    >== select  BOOK.* , READER.*  from tb_book BOOK left join tb_reader READER  on  BOOK.READER_ID =  READER.ID  where  BOOK.ID >  ?  
params >== 0
rawsql >== select  BOOK.* , READER.*  from tb_book BOOK left join tb_reader READER  on  BOOK.READER_ID =  READER.ID  where  BOOK.ID >  0  
count  <== 2
```
### 一对多关联查询
一个读者会借阅多本图书，所以tb_reader表和tb_book表为一对多的关系
tb_reader表中的id和tb_book表中的reader_id存在关联关系

```java
Sql sql = select(READER.ALL,BOOK.ALL).from(READER).leftJoin(BOOK).on(BOOK.READER_ID.eq(READER.ID)).where(READER.ID.gt(0));
Record record = sqlExecutor.selectList(sql);
//从结果集取出Reader数据
List<Reader> readerList = record.getList(Reader.class);
//从结果集取出Book数据
List<Book> bookList = record.getList(Book.class);
//由于是关联查询，会造成readerList数据重复，所以要先去除下重复数据
BeanUtils.distinct(readerList);
//转换为DTO
List<ReaderDTO> readerDTOList = BeanUtils.copyList(readerList,ReaderDTO.class);
//ReaderDTO中有List<Book> bookList属性，合并
BeanUtils.merge(readerDTOList,bookList,"id","readerId");
```
打印sql语句

```sql
sql    >== select  READER.* , BOOK.*  from tb_reader READER left join tb_book BOOK  on  BOOK.READER_ID =  READER.ID  where  READER.ID >  ?  
params >== 0
rawsql >== select  READER.* , BOOK.*  from tb_reader READER left join tb_book BOOK  on  BOOK.READER_ID =  READER.ID  where  READER.ID >  0  
count  <== 2
```
## 分页查询

```java
Sql sql = select(BOOK.ALL).from(BOOK).where(BOOK.ID.gt(0)).and(BOOK.DESCRIPTION.like("'%测试%'"));
Record record = sqlExecutor.selectPage(sql,0,10);
//总页数
long total = record.getTotal();
//页内数据
List<Book> bookList = record.getList(Book.class);
```

## 复杂sql语句查询

### 统计分析类


```java
Sql sql = select(MySql.avg(STUDENT_SCORE.SCORE),STUDENT_SCORE.STUDENT_NAME)
        .from(STUDENT_SCORE)
        .where(MySql.dateFormat(STUDENT_SCORE.CREATE_TIME,"'%Y-%m-%d'").eq("'2022-05-01'"))
        .groupBy(STUDENT_SCORE.STUDENT_NAME);

List<StudentScore> studentScoreList = sqlExecutor.selectList(sql, StudentScore.class);
System.out.println(JSON.toJSONString(studentScoreList,SerializerFeature.PrettyFormat));
```
打印的sql语句

```sql
sql    >== select  avg(  STUDENT_SCORE.SCORE ) as SCORE , STUDENT_SCORE.STUDENT_NAME  from tb_student_score STUDENT_SCORE where  DATE_FORMAT (CREATE_TIME,'%Y-%m-%d')  = '2022-05-01'  group by  STUDENT_SCORE.STUDENT_NAME 
count  <== 2
```

### 子查询类


```java
Sql sql = select(BOOK.ALL).from(BOOK)
        .where(BOOK.ID.in(new SubSql(
                select(BOOK_CATALOG.BOOK_ID).from(BOOK_CATALOG)
                        .where(BOOK_CATALOG.CATALOG_ID.in(new int[]{1,2,3,4,5}))
                        )
                )
        );
List<Book> bookList = sqlExecutor.selectList(sql,Book.class);
System.out.println(JSON.toJSONString(bookList, SerializerFeature.PrettyFormat));
```
打印的sql语句

```sql
sql    >== select  BOOK.*  from tb_book BOOK where  BOOK.ID in  ( select  BOOK_CATALOG.BOOK_ID  from tb_book_catalog BOOK_CATALOG where  BOOK_CATALOG.CATALOG_ID in  (1,2,3,4,5)   )  
count  <== 1
```
## insert udate delete
insert update delete相对简单，就不写实例代码了
## 更复杂的sql语句

jsql的目标是能在开发中简化90%以上的和数据库相关操作，有很多非常复杂sql语句jsql可能适应不了，对于这种情况，完全可以写原生sql语句然后通过JdbcTemplate执行。
jsql的springboot实现是基于JdbcTemplate的，他们共用相同的数据源已经事务等。