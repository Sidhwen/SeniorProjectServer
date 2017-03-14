package metj.models;

import metj.models.*;
import java.sql.*;

public class DataProcessor {

	Connection Conn = null;
	boolean LoginSuccess = false;
	String myDOD_EDI_PI;
	String myPassword;
	Language[] myLanguage = new Language[5];
	ASVABModel myASVABModel;
	AwardsModel myAwardsModel;
	NECHistoryModel myNECHistoryModel;
	NECModel myNECModel;
	NOBCHistoryModel myNOBCHistoryModel;
	NOBCModel myNOBCModel;
	QualCertModel myQualCertModel;
	TrainingModel myTrainingModel;
	
	public DataProcessor (String DOD_EDI_PI, String Password, Connection MyConn) throws SQLException
	{
		myDOD_EDI_PI = DOD_EDI_PI;
		Conn = MyConn;
		myPassword = Password;
		if (Login())
		{
			LoginSuccess = true;
			for (int i = 0; i < 5; i++)
			{
				myLanguage[i] = new Language(myDOD_EDI_PI, i + 1, Conn);
			}
			myNECModel = new NECModel(myDOD_EDI_PI, Conn);
			myASVABModel = new ASVABModel(myDOD_EDI_PI, Conn);
			myAwardsModel = new AwardsModel(myDOD_EDI_PI, Conn);
			myNECHistoryModel = new NECHistoryModel(myDOD_EDI_PI, Conn);
			myNOBCHistoryModel = new NOBCHistoryModel(myDOD_EDI_PI, Conn);
			myNOBCModel = new NOBCModel(myDOD_EDI_PI, Conn);
			myQualCertModel = new QualCertModel(myDOD_EDI_PI, Conn);
			myTrainingModel = new TrainingModel(myDOD_EDI_PI, Conn);
		}

		}
	
	private boolean Login() throws SQLException
	{
		ResultSet Rs = null;
		Statement Stmt = null;
		Stmt = Conn.createStatement();
		
		String RealPassword;
        Rs = Stmt.executeQuery("SELECT PIN FROM LOGIN WHERE DOD_EDI_PI = '" + myDOD_EDI_PI + "'");

        RealPassword = Rs.getString(1);
        Stmt.close();
        if (RealPassword.equals(myPassword))
        {
        	return true;
        }
		return false;
	}
	// construct the ends of INSERT INTO Sql statements to send over to the client, delimited with new lines.
	public String print()
	{
		String OutputString = "";
		OutputString += myASVABModel.print();
		OutputString += myAwardsModel.print();
		OutputString += "1\n(" + myDOD_EDI_PI + ",";
		for (int i = 0; i < 5; i++)
		{
			OutputString += myLanguage[i].print();
			if (i != 4) { OutputString += ","; }
		}
		OutputString += ")\n";
		OutputString += myNECHistoryModel.print();
		OutputString += myNECModel.print();
		OutputString += myNOBCHistoryModel.print();
		OutputString += myNOBCModel.print();
		OutputString += myQualCertModel.print();
		OutputString += myTrainingModel.print();
		return OutputString;
	}
	}
	
		
	

