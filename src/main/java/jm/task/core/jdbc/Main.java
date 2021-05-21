package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {

       UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
        userDao.dropUsersTable();
        Util.getSessionFactory().close();

        /*UserDao userDao = new UserDao();
        userDao.createUsersTable();
        for (int i = 1; i<=4; i++) {
            userDao.saveUser("Имя " + i, " Фамилия" + i, (byte) i);
            System.out.println("User с именем  - Имя " + i + " добавлен в базу данных");
        }
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();*/
    }
}
