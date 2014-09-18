/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author CPU
 */
public interface Transaction {

	void trans(Connection conn) throws SQLException;

}
