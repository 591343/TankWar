import java.sql.*;

public class Conndatum {//������Conn
    static Connection con;//����Connection����
    static PreparedStatement sql;
    static ResultSet res; //����ResultSe����
    private ScoreRank rank;//���а�
    private String url="jdbc:mysql://localhost:3306/scorerank?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    public Connection getConnection() {  //��������ֵΪConnection����
    	try {                      //��������������
    		Class.forName("com.mysql.jdbc.Driver"); 
    		System.out.println("���ݿ��������سɹ�");
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	try {
    		con=DriverManager.getConnection(url,"root","591343671");
    		System.out.println("���ݿ����ӳɹ�");
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return con;
    
    }
    
    
    public void addData(String name,int score,long time) {
    	Integer a=score;
    	String scoreW=a.toString();
        Integer b=(int) time;
        String passtime=b.toString();
    	con=getConnection();//�����������ݿ�ķ���
    	try {
    		sql=con.prepareStatement("select * from tb_score ");//��ѯ���ݿ�
    		res=sql.executeQuery();//ִ��SQL���
    		sql=con.prepareStatement("insert into tb_score (name,score,passtime) values('"+name+"','"+scoreW+"','"+passtime+"')");
    		
    		
    		sql.executeUpdate();//ִ�и���
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	
   
    }
    
   public void showRank() {//չʾ���а�
	   con=getConnection();//�����������ݿ�ķ���
	   rank=new ScoreRank();
	   try {
			sql=con.prepareStatement("select * from  tb_score order by passtime");//��ѯ���ݿ�
    		res=sql.executeQuery();//ִ��SQL���
    		rank.rankArea.append("\t     ̹�˴�ս���а�\n");
    		rank.rankArea.append("Id\tName\tScore\tPassTime\n");
    		while(res.next()) {
    			int id=res.getInt(1);
        		String name=res.getString("name");
        		String score=res.getString("score");
        		String passtime=res.getString("passtime");
        		//System.out.println(id+name+score);
    			rank.rankArea.append(id+"\t"+name+"\t"+score+"\t"+passtime+"S"+"\n");
    		
    		}
    		res.close();
    		    rank.init();//���а��ʼ��
	   }catch (Exception e) {
		// TODO: handle exception
		   e.printStackTrace();
		   System.out.println("ass");
	}
	  
   }
   
   public void shutRank() {
	   //ǿ�йرմ���
	  rank.dispose();
   }
    
 
}
