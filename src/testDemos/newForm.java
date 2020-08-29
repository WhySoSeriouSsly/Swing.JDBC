package testDemos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Core.Utilities.Messages.Messages;
import Core.Utilities.Messages.SqlCommand;
import DataAccess.DbHelper;
import Entities.Country;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class newForm extends JFrame {

	private DefaultTableModel model;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtSearchKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newForm frame = new newForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public newForm() {
		InitForm();
	}

	public void InitForm() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 428);
		contentPane = new JPanel();
		setContentPane(contentPane);

		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test _form = new test();
				_form.setVisible(true);
				dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(newForm.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblSearchKey = new JLabel("Search Key");

		txtSearchKey = new JTextField();
		txtSearchKey.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSearchActionPerformed(e);
			}

		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnBack))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(49).addGroup(gl_contentPane
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblSearchKey).addGap(18)
										.addComponent(txtSearchKey, GroupLayout.PREFERRED_SIZE, 127,
												GroupLayout.PREFERRED_SIZE)
										.addGap(39).addComponent(btnSearch))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 490,
										GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(64, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnBack).addGap(20)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnSearch)
								.addComponent(lblSearchKey).addComponent(txtSearchKey, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(46, Short.MAX_VALUE)));

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Country Code", "Country Name", "Population", "Region" }));
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}

	private void btnSearchActionPerformed(ActionEvent e) {
		populate();
	}

	private void populate() {
		model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		ArrayList<Country> countries2 = getCountries();
		if(countries2.size()==0)
		{
			String exception=Messages.SearchException;
			JOptionPane.showMessageDialog(null, exception);
			return;
		}
		for (Country country : countries2) {
			Object[] row = { country.getCode(), country.getName(), country.getPopulation(), country.getRegion() };
			model.addRow(row);
		}
		String successMessage=Messages.SearchSuccess;
		JOptionPane.showMessageDialog(null, successMessage);
	
	}

	private ArrayList<Country> getCountries() {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		ResultSet resultSet;
		ArrayList<Country> countries = null;
		PreparedStatement statement2 = null;

		try {
			connection = dbHelper.getConnection();
			statement2 = connection.prepareStatement(SqlCommand.SearchCountryName);
			statement2.setString(1, txtSearchKey.getText());
			resultSet = statement2.executeQuery();
			countries = new ArrayList<Country>();
			while (resultSet.next()) {
				countries.add(new Country(resultSet.getString("Code"), resultSet.getString("Name"),
						resultSet.getInt("Population"), resultSet.getString("Region")));
			}

		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, exception);
			dbHelper.showErrorMessage(exception);
		} finally {
			try {
				statement2.close();
				connection.close();
			} catch (SQLException ex) {

			}
		}
		return countries;
	}
}
