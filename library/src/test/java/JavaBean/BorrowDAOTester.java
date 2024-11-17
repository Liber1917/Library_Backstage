//package cn.karlxing.JavaBean;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.List;
//
//public class BorrowDAOTester {
//    public static void main(String[] args) {
//        Connection connection = null;
//        BorrowDAO dao = null;
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC",
//                    "root", ""
//            );
//            dao = new BorrowDAO(connection);
//
//            // 测试添加借还记录
//            BorrowPO borrow = new BorrowPO(0, 1, 1, 20240101, -1);
//            boolean result = dao.addBorrow(borrow);
//            System.out.println("添加借还记录结果: " + result);
//
//            // 测试获取借还记录
//            BorrowPO retrievedBorrow = dao.getBorrow(borrow.getId());
//            System.out.println("获取借还记录: " + retrievedBorrow);
//
//            // 测试更新借还记录
//            retrievedBorrow.setReturnDate(20240201);
//            result = dao.updateBorrow(retrievedBorrow);
//            System.out.println("更新借还记录结果: " + result);
//
//            // 测试删除借还记录
//            result = dao.deleteBorrow(retrievedBorrow.getId());
//            System.out.println("删除借还记录结果: " + result);
//
//            // 测试获取所有借还记录
//            List<BorrowPO> allBorrows = dao.getAllBorrows();
//            System.out.println("所有借还记录: " + allBorrows);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}