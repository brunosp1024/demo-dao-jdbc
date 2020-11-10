package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		List<Department> list = depDao.findAll();
		for(Department obj : list) {
			System.out.println(obj);
		}
		
		/*
		Department dep = depDao.findById(4);
		System.out.println(dep);
		
		
		Department dep = new Department(3, null);
		dep.setName("Music");
		depDao.update(dep);

		Department dep = new Department(null, "Development");
		depDao.insert(dep);
		System.out.println("Inserted! Id: " + dep.getId());
		System.out.println(dep);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller sel = sellerDao.findById(3);
		System.out.println(sel);

		System.out.println("----------------------------------------------------------------------");

		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("----------------------------------------------------------------------");

		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("----------------------------------------------------------------------");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Seller seller = new Seller(null, "Goku", "goku@gmail.com", sdf.parse("13/04/1991"), 2500.0,
				new Department(3, null));
		sellerDao.insert(seller);
		System.out.println("Inserted: new Id: " + seller.getId());

		System.out.println("----------------------------------------------------------------------");

		Seller sell = sellerDao.findById(1);
		sell.setName("Maria bendita");
		sellerDao.update(sell);
		System.out.println("Update completed!");
		
		System.out.println("----------------------------------------------------------------------");
		
		sellerDao.deleteById(11);*/


	}

}
