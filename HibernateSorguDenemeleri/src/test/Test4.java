package test;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;

import entities.Birlik;
import entities.Personel;
import entities.PersonelAdres;

public class Test4 {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		Properties properties = new Properties();
		properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
		properties.put(Environment.PASS, "harita");
		properties.put(Environment.USER, "harita_user");
		properties.put(Environment.URL, "jdbc:mysql://localhost:3306/harita?serverTimezone=UTC");

		properties.put(Environment.SHOW_SQL, "true");

		configuration.setProperties(properties);

		configuration.addAnnotatedClass(Personel.class);
		configuration.addAnnotatedClass(PersonelAdres.class);
		configuration.addAnnotatedClass(Birlik.class);

		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		//@formatter:off
		NativeQuery query = session.createSQLQuery("select p.adi, p.soyadi, count(*) adres_sayisi from personel p " + 
		                                            "join personel_adres a on (p.id = a.personel_id) group by adi, soyadi");
     	//@formatter:on

		List sonuc = query.list();
		for (int i = 0; i < sonuc.size(); i++) {
			Object[] o = (Object[]) sonuc.get(i);
			for (int j = 0; j < o.length; j++) {
				System.out.print(o[j]);
				if (j < o.length - 1) {
					System.out.print(",");
				}
			}
			System.out.println();
		}
	}

}
