package testDemos;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Business.Validation.Validator;
import Core.Utilities.Messages.Messages;
import Core.Utilities.Messages.SqlCommand;
import DataAccess.DbHelper;
import Entities.Country;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class test extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	private JTextField txtCode;
	private JTextField txtCountryName;
	private JTextField txtPopulation;
	private JTextField txtRegion;
	private JButton btnSave;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JMenuBar menuBar;
	private JMenu mnMenu1;
	private JMenuItem mnSearchCountryName;
	private JMenu mMenu2;
	private JTextField txtSearchKey;
	private JMenuItem mnSearchCountryCode;
	private JMenuItem mnSearchCountryRegion;
	private JLabel lblMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
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
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Store System\r\n");
		initComponents();
		populateTable();
	}

	public void initComponents() {
		setBounds(100, 100, 755, 592);
		contentPane = new JPanel();
		setContentPane(contentPane);
		this.setSize(753, 601);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Country Code", "Country Name", "Population", "Region" }));
		scrollPane.setViewportView(table);

		menuBar = new JMenuBar();

		mnMenu1 = new JMenu("Menu 1");
		mnMenu1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(mnMenu1);

		mnSearchCountryName = new JMenuItem("Search by Country Name");
		mnSearchCountryName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newForm form = new newForm();
				form.setVisible(true);
				dispose();
			}
		});

		mnSearchCountryCode = new JMenuItem("Search by Country Code");
		mnSearchCountryCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchbycountrycode searchForm = new searchbycountrycode();
				searchForm.setVisible(true);
				dispose();
			}
		});
		mnMenu1.add(mnSearchCountryCode);

		mnMenu1.add(mnSearchCountryName);

		mnSearchCountryRegion = new JMenuItem("Search by Region");
		mnMenu1.add(mnSearchCountryRegion);

		mMenu2 = new JMenu("Menu 2");
		mMenu2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(mMenu2);

		JLabel lblCountryCode = new JLabel("Code");

		JLabel lblCountryName = new JLabel("Name");

		JLabel lblPopulation = new JLabel("Continent");

		JLabel lblCountryRegion = new JLabel("Region");

		txtCode = new JTextField();
		txtCode.setColumns(10);

		txtCountryName = new JTextField();
		txtCountryName.setColumns(10);

		txtPopulation = new JTextField();
		txtPopulation.setColumns(10);

		txtRegion = new JTextField();
		txtRegion.setColumns(10);
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddActionPerformed(evt);
			}

		});
		btnSave.setIcon(
				new ImageIcon(test.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDeleteActionPerformed(evt);
			}
		});
		btnDelete.setIcon(new ImageIcon(test.class.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnUpdateActionPerformed(evt);
			}

		});
		btnUpdate.setIcon(new ImageIcon(test.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));

		JLabel lblSearchKey = new JLabel("Search Key");

		txtSearchKey = new JTextField();
		txtSearchKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				txtSearchKeyReleased(arg0);
			}
		});

		txtSearchKey.setColumns(10);

		lblMessage = new JLabel("");

		JButton btnSelectDelete = new JButton("Select Delete");
		btnSelectDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnDeletedSelectedActionPerfomerd(arg0);
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 711, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(64)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addGap(42)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
								.addGap(37)
								.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(91).addGroup(gl_contentPane
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCountryCode, GroupLayout.PREFERRED_SIZE, 46,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(txtCode, GroupLayout.PREFERRED_SIZE, 170,
												GroupLayout.PREFERRED_SIZE)
										.addGap(41)
										.addComponent(lblPopulation, GroupLayout.PREFERRED_SIZE, 56,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(txtPopulation, GroupLayout.PREFERRED_SIZE, 170,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblCountryName, GroupLayout.PREFERRED_SIZE, 46,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(txtCountryName, GroupLayout.PREFERRED_SIZE, 170,
												GroupLayout.PREFERRED_SIZE)
										.addGap(41)
										.addComponent(lblCountryRegion, GroupLayout.PREFERRED_SIZE, 56,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10).addComponent(txtRegion, GroupLayout.PREFERRED_SIZE, 170,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(64).addGroup(gl_contentPane
								.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 609, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblSearchKey, GroupLayout.PREFERRED_SIZE, 71,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtSearchKey, GroupLayout.PREFERRED_SIZE, 146,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(Alignment.TRAILING,
														gl_contentPane.createSequentialGroup().addComponent(lblMessage)
																.addGap(1))
												.addGroup(Alignment.TRAILING,
														gl_contentPane.createSequentialGroup()
																.addComponent(btnSelectDelete).addGap(48)))))))
				.addContainerGap(24, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(34)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblSearchKey)
						.addComponent(txtSearchKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMessage).addComponent(btnSelectDelete))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(3).addComponent(lblCountryCode))
						.addComponent(txtCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(3).addComponent(lblPopulation))
						.addComponent(txtPopulation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(34)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(3).addComponent(lblCountryName))
						.addComponent(txtCountryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(3).addComponent(lblCountryRegion))
						.addComponent(txtRegion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
				.addGap(66)));
		contentPane.setLayout(gl_contentPane);

		// pack()=lock.
	}

	private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtSearchKeyReleased
		String searchKey = txtSearchKey.getText();
		TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(tableRowSorter);
		tableRowSorter.setRowFilter(RowFilter.regexFilter(searchKey));
	}// GEN-LAST:event_txtSearchKeyReleased

	private void populateTable() {

		model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		try {
			ArrayList<Country> countries = getCountries();
			for (Country country : countries) {
				Object[] row = { country.getCode(), country.getName(), country.getPopulation(), country.getRegion() };
				model.addRow(row);
			}
		} catch (SQLException ex) {

		}
	}

	private ArrayList<Country> getCountries() throws SQLException {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet;
		ArrayList<Country> countries = null;

		try {
			connection = dbHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SqlCommand.SearchCountry);
			countries = new ArrayList<Country>();
			while (resultSet.next()) {
				countries.add(new Country(resultSet.getString("Code"), resultSet.getString("Name"),
						resultSet.getInt("Population"), resultSet.getString("Region")));
			}

		} catch (SQLException exception) {
			dbHelper.showErrorMessage(exception);
		} finally {
			statement.close();
			connection.close();
		}
		return countries;
	}

	private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
		Connection connection = null;
		DbHelper dbHelper = new DbHelper();
		PreparedStatement statement = null;
		try {
			connection = dbHelper.getConnection();
			String sql = SqlCommand.CountryAdd;
			statement = connection.prepareStatement(sql);
			if (Validator.validate(txtCode) == false || Validator.validate(txtCountryName) == false
					|| Validator.validate(txtPopulation) == false || Validator.validate(txtRegion) == false) {
				JOptionPane.showMessageDialog(null, Messages.ValidationException);
				return;
			}
			statement.setString(1, txtCode.getText());
			statement.setString(2, txtCountryName.getText());
			statement.setInt(3, Integer.valueOf(txtPopulation.getText()));
			statement.setString(4, txtRegion.getText());
			int result = statement.executeUpdate();
			populateTable();
			JOptionPane.showMessageDialog(null, Messages.ProductAdded);
			// lblMessage.setText("Country Added");
		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, Messages.ProductNotAdded);
			dbHelper.showErrorMessage(exception);

		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException ex) {

			}
		}
	}
	//POST
	private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
		Connection connection = null;
		DbHelper helper = new DbHelper();
		PreparedStatement statement = null;
		ResultSet resultSet;
		try {
			connection = helper.getConnection();
			String sql = SqlCommand.CountryDelete;
			statement = connection.prepareStatement(sql);
			if (Validator.validate(txtCode) == false) {
				JOptionPane.showMessageDialog(null, Messages.ValidationException);
				return;
			}
			statement.setString(1, txtCode.getText());
			int result = statement.executeUpdate();
			populateTable();
			JOptionPane.showMessageDialog(null, Messages.ProductDeleted);
			// System.out.println("Kayýt silindi");
			// lblMessage.setText("Country Deleted" + txtCode.getText());
		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, Messages.ProductNotDeleted);
			helper.showErrorMessage(exception);
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException ex) {

			}
		}
	}

	// POST
	private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
		Connection connection = null;
		DbHelper helper = new DbHelper();
		PreparedStatement statement = null;
		ResultSet resultSet;
		try {
			connection = helper.getConnection();
			String sql = SqlCommand.CountryUpdate;
			statement = connection.prepareStatement(sql);
			if (Validator.validate(txtCode) == false || Validator.validate(txtCountryName) == false
					|| Validator.validate(txtPopulation) == false || Validator.validate(txtRegion) == false) {
				JOptionPane.showMessageDialog(null, Messages.ValidationException);
				return;
			}
			statement.setString(1, txtCountryName.getText());
			statement.setInt(2, Integer.valueOf(txtPopulation.getText()));
			statement.setString(3, txtRegion.getText());
			statement.setString(4, txtCode.getText());
			statement.executeUpdate();
			populateTable();
			JOptionPane.showMessageDialog(null, Messages.ProductUpdated);
			// lblMessage.setText("Country Updated." + txtCode.getText());

		} catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, Messages.ProductNotUpdated);
			helper.showErrorMessage(exception);
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException ex) {
			}
		}
	}

	// POST
	private void btnDeletedSelectedActionPerfomerd(java.awt.event.ActionEvent evt) {

		Connection connection = null;
		DbHelper helper = new DbHelper();
		PreparedStatement statement = null;
		model = (DefaultTableModel) table.getModel();
		int row = table.getSelectedRow();
		if (row == -1) {
			String exceptionMessage = Messages.TableNotEmpty;
			JOptionPane.showMessageDialog(null, exceptionMessage);
			return;
		}
		String delRow = SqlCommand.SelectCountryDeleteSql;
		String CodeValue = table.getModel().getValueAt(row, 0).toString();

		try {
			connection = helper.getConnection();
			statement = connection.prepareStatement(delRow);
			statement.setString(1, CodeValue);
			statement.execute();
			JOptionPane.showMessageDialog(null, Messages.ProductDeleted);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		populateTable();
	}
}