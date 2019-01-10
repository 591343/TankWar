import java.sql.*;

public class Conndatum {//创建类Conn
    static Connection con;//声明Connection对象
    static PreparedStatement sql;
    static ResultSet res; //声明ResultSe对象
    private ScoreRank rank;//排行榜
    private String url="jdbc:mysql://localhost:3306/scorerank?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    public Connection getConnection() {  //建立返回值为Connection方法
    	try {                      //加载数据驱动类
    		Class.forName("com.mysql.jdbc.Driver"); 
    		System.out.println("数据库驱动加载成功");
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	try {
    		con=DriverManager.getConnection(url,"root","591343671");
    		System.out.println("数据库连接成功");
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return con;
    
    }
    
    
    public void addData(String name,int score) {
    	Integer a=score;
    	String scoreW=a.toString();
    
    	con=getConnection();//调用连接数据库的方法
    	try {
    		sql=con.prepareStatement("select * from tb_score");//查询数据库
    		res=sql.executeQuery();//执行SQL语句
    		sql=con.prepareStatement("insert into tb_score (name,score) values('"+name+"','"+scoreW+"')");
    		
    		
    		sql.executeUpdate();//执行更新
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	
   
    }
    
   public void showRank() {//展示排行榜
	   con=getConnection();//调用连接数据库的方法
	   rank=new ScoreRank();
	   try {
			sql=con.prepareStatement("select * from tb_score");//查询数据库
    		res=sql.executeQuery();//执行SQL语句
    		rank.rankArea.append("Id\tName\tScore\n");
    		while(res.next()) {
    			int id=res.getInt(1);
        		String name=res.getString("name");
        		String score=res.getString("score");
        		//System.out.println(id+name+score);
    			rank.rankArea.append(id+"\t"+name+"\t"+score+"\n");
    		
    		}
    		    rank.init();//排行榜初始化
	   }catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
		   System.out.println("ass");
	}
	  
   }
    
 
}
