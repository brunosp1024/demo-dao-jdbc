package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	//Classe responsável por instanciar as interfaces Daos (SellerDao, DepartmentDao). 
	
	public static SellerDao createSellerDao() { //O retorno e do tipo interface, mas a instancia interna é uma classe com os métodos implementados.
		return new SellerDaoJDBC(DB.getConnection()); //classe com os métodos da innterface Dao implementada.
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}

}
