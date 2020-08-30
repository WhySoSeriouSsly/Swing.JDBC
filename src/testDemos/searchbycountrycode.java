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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Business.Validation.Validator;
import Business.Validation.ValidatorToolTest;
import Core.Utilities.Messages.Messages;
import Core.Utilities.Messages.SqlCommand;
import DataAccess.DbHelper;
import Entities.Country;

public class searchbycountrycode extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearchCountryCode;
	private JTable table;
	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					searchbycountrycode frame = new searchbycountrycode();
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
	public searchbycountrycode() {
		init();
	}

	public void init() {
		setBounds(100, 100, 644, 429);
		contentPane = new JPanel();
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test _homeForm = new test();
				_homeForm.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(
				searchbycountrycode.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));

		JScrollPane scrollPane = new JScrollPane();

		JButton btnCodeSearch = new JButton("Search");
		btnCodeSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnCountryCodeSearch(e);
			}

		});

		JLabel lblCountryCode = new JLabel("Country Code");

		txtSearchCountryCode = new JTextField();
		txtSearchCountryCode.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 603, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblCountryCode).addGap(18)
										.addComponent(txtSearchCountryCode, GroupLayout.PREFERRED_SIZE, 126,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnCodeSearch)))
						.addContainerGap(15, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCountryCode)
						.addComponent(txtSearchCountryCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCodeSearch))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Country Code", "Country Name", "Population", "Region" }));
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);

	}

	private void btnCountryCodeSearch(ActionEvent e) {
		getCountries();

	}

//	private void populate() {
//		model = (DefaultTableModel) table.getModel();
//		model.setRowCount(0);
//		ArrayList<Country> countries2 = getCountries();
//
//		if (countries2.size() == 0) {
//			String exception = Messages.SearchException;
//			JOptionPane.showMessageDialog(null, exception);
//			txtSearchCountryCode.setText("");
//			return;
//		}
//		for (Country country : countries2) {
//			Object[] row = { country.getCode(), country.getName(), country.getPopulation(), country.getRegion() };
//			model.addRow(row);
//		}
//		String successMessage = Messages.SearchSuccess;
//		JOptionPane.showMessageDialog(null, successMessage);
//		txtSearchCountryCode.setText("");
//	}

	private Connection connection = null;
	private DbHelper dbHelper = new DbHelper();
	private ResultSet resultSet;
	private ArrayList<Country> countries = null;
	private PreparedStatement statement2 = null;

	private void getCountries() {
		try {
			connection = dbHelper.getConnection();
			String sql=txtSearchCountryCode.getText();
			statement2 = connection.prepareStatement(SqlCommand.getLikeCodeSql(sql));
//			if(Validator.validate(txtSearchCountryCode)==false) {
//				JOptionPane.showMessageDialog(null, Messages.ValidationException);
//				return;
//			}
			if(ValidatorToolTest.validate(txtSearchCountryCode)!=true) {
				return;
			};
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

		model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		ArrayList<Country> countries2 = countries;

		if (countries2.size() == 0) {
			String exception = Messages.SearchException;
			JOptionPane.showMessageDialog(null, exception);
			txtSearchCountryCode.setText("");
			return;
		}
		for (Country country : countries2) {
			Object[] row = { country.getCode(), country.getName(), country.getPopulation(), country.getRegion() };
			model.addRow(row);
		}
		String successMessage = Messages.SearchSuccess;
		JOptionPane.showMessageDialog(null, successMessage);
		txtSearchCountryCode.setText("");
	}

}
