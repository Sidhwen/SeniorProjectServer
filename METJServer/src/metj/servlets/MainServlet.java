package metj.servlets;

import java.io.*;
import java.sql.*;
import metj.models.DataProcessor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection Conn = null;
	private String DBURL = "jdbc:sqlite:C:/databases/NUWC.db";
    /**
     * Default constructor. 
     */
    public MainServlet() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated constructor stub
    	Class.forName("org.sqlite.JDBC");
    	Conn = DriverManager.getConnection(DBURL);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        PrintWriter out = response.getWriter();
        String myDOD_EDI_PI = request.getParameter("DOD_EDI_PI");
        String myPass = request.getParameter("Pass");
       // if (!myDOD_EDI_PI.contains("'") && !myPass.contains("'"))
      //  {
        try {
        
        DataProcessor myProcessor = new DataProcessor(myDOD_EDI_PI, myPass, Conn);
        
        out.println(myProcessor.print());
        }
        catch (SQLException e) {
        	out.println(e.getMessage());
        	out.println( e.getErrorCode());
        	out.println(e.getSQLState());
        }
   //     }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
