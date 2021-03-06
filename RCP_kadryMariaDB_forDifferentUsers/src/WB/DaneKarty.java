package WB;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class DaneKarty extends JFrame {

	private JPanel contentPane;
	private JTextField txt1;
	private JTextField textField_nrKarty;
	private JTextField txt2;
	private JTextField textField_nazwImie;
	private JTextField txt3;
	private JTextField textField_nrHacoSoft;
	private JButton btnArchiwizujKarte;
	private JTextField txt4;
	private JTextField textField_firma;
	private JTextField txt5;
	private JTextField textField_stanowisko;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
				try {
					DaneKarty frame = new DaneKarty(connection);
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
	public DaneKarty(final Connection connection) {
		
		setBackground(Color.WHITE);
		setTitle("Przypisz dane pracownika do karty");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 467, 570);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMojeMenu = new JLabel("Przypisz dane pracownika");
		lblMojeMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMojeMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		// co je�li u�ytkownik jest adminem
		
		if (Login.getAdmin()==true)
		{
		
		}
		
		textField_nrKarty = new JTextField();
		textField_nrKarty.setHorizontalAlignment(SwingConstants.CENTER);
		textField_nrKarty.setColumns(10);
		textField_nrKarty.grabFocus();
		textField_nrKarty.requestFocus();
		
		txt2 = new JTextField();
		txt2.setText("NAZWISKO I IMIE");
		txt2.setHorizontalAlignment(SwingConstants.CENTER);
		txt2.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt2.setEditable(false);
		txt2.setColumns(10);
		
		textField_nazwImie = new JTextField();
		textField_nazwImie.setHorizontalAlignment(SwingConstants.CENTER);
		textField_nazwImie.setColumns(10);
		
		txt1 = new JTextField();
		txt1.setText("NR KARTY");
		txt1.setHorizontalAlignment(SwingConstants.CENTER);
		txt1.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt1.setEditable(false);
		txt1.setColumns(10);
		
		txt3 = new JTextField();
		txt3.setText("NR Z HACOSOFTA");
		txt3.setHorizontalAlignment(SwingConstants.CENTER);
		txt3.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt3.setEditable(false);
		txt3.setColumns(10);
		
		textField_nrHacoSoft = new JTextField();
		textField_nrHacoSoft.setHorizontalAlignment(SwingConstants.CENTER);
		textField_nrHacoSoft.setColumns(10);
		
		txt4 = new JTextField();
		txt4.setText("FIRMA");
		txt4.setHorizontalAlignment(SwingConstants.CENTER);
		txt4.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt4.setEditable(false);
		txt4.setColumns(10);
		
		textField_firma = new JTextField();
		textField_firma.setHorizontalAlignment(SwingConstants.CENTER);
		textField_firma.setColumns(10);
		
		txt5 = new JTextField();
		txt5.setText("STANOWISKO");
		txt5.setHorizontalAlignment(SwingConstants.CENTER);
		txt5.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt5.setEditable(false);
		txt5.setColumns(10);
		
		textField_stanowisko = new JTextField();
		textField_stanowisko.setHorizontalAlignment(SwingConstants.CENTER);
		textField_stanowisko.setColumns(10);
		
		JButton btnNowaKarta = new JButton("Nowa karta");
		btnNowaKarta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(textField_nrKarty.getText().equals("")||textField_nazwImie.getText().equals("")||textField_nrHacoSoft.getText().equals("")||textField_firma.getText().equals("")||textField_stanowisko.getText().equals(""))){
					Statement st = null;
					ResultSet rs = null;
					try {
						st = connection.createStatement();
						rs = st.executeQuery("SELECT id_karty FROM cards_name_surname_nrHacoSoft USE INDEX (idx_id_karty) WHERE id_karty='"+textField_nrKarty.getText()+"'");
						if(rs.next()){
							JOptionPane.showMessageDialog(null, "Taka karta juz istnieje - mozesz zmodyfikowac jej dane");
						}else{
							st.close();
							rs.close();
							Statement st2 = null;
							ResultSet rs2 = null;
							try {
								st2 = connection.createStatement();
								rs2 = st2.executeQuery("SELECT nazwisko_imie FROM cards_name_surname_nrHacoSoft USE INDEX (idx_nazwisko_imie) WHERE nazwisko_imie='"+textField_nazwImie.getText()+"'");
								if(rs2.next()){
									JOptionPane.showMessageDialog(null, "Ten pracownik ma ju� swoj� kart�");
								}else{
									st2.close();
									rs2.close();
									Statement st3 = null;
									ResultSet rs3 = null;
									try {
										st3 = connection.createStatement();
										rs3 = st3.executeQuery("SELECT HacoSoftnumber FROM cards_name_surname_nrHacoSoft USE INDEX (idx_hacosoftnumber) WHERE HacoSoftnumber='"+textField_nrHacoSoft.getText()+"' AND HacoSoftnumber<>0");
										if(rs3.next()){
											JOptionPane.showMessageDialog(null, "Ten numer jest ju� u�ywany w HacoSofcie");
										}else{
											st3.close();
											rs3.close();
											try {
												String query="INSERT INTO cards_name_surname_nrHacoSoft (id_karty, nazwisko_imie, HacoSoftnumber, stanowisko, firma) VALUES ('"+textField_nrKarty.getText()+"', '"+textField_nazwImie.getText()+"', '"+textField_nrHacoSoft.getText()+"', '"+textField_stanowisko.getText()+"', '"+textField_firma.getText()+"')";
												PreparedStatement pst=connection.prepareStatement(query);
												pst.execute();
												pst.close();
											}catch (SQLException e2) {
												// TODO Auto-generated catch block
												JOptionPane.showMessageDialog(null, "Sprobuj jeszcze raz - baza jest zajeta");
												e2.printStackTrace();
											}
										}
										st3.close();
										rs3.close();
									}catch (SQLException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
								}
								st2.close();
								rs2.close();
							}catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
						st.close();
						rs.close();
					}catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Nie wszystkie pola sa uzupelnione");
				}
			}
		});
		btnNowaKarta.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnSprawdzDaneKarty = new JButton("Sprawdz dane karty");
		btnSprawdzDaneKarty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_nrKarty.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Nie wpisano nr karty");
				}else{
					Statement st = null;
					ResultSet rs = null;
					try {
						st = connection.createStatement();
						rs = st.executeQuery("SELECT id_karty, nazwisko_imie, HacoSoftnumber, stanowisko, firma FROM cards_name_surname_nrHacoSoft USE INDEX (idx_id_karty) WHERE id_karty='"+textField_nrKarty.getText()+"'");
						if(rs.next()){
							textField_nazwImie.setText(rs.getString("nazwisko_imie"));
							textField_nrHacoSoft.setText(rs.getString("HacoSoftnumber"));
							textField_stanowisko.setText(rs.getString("stanowisko"));
							textField_firma.setText(rs.getString("firma"));
							st.close();
							rs.close();
						}else{
							JOptionPane.showMessageDialog(null, "Nie ma takiego nr karty w bazie");
						}
						st.close();
						rs.close();
					}catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		btnSprawdzDaneKarty.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnUsuKart = new JButton("Usu\u0144 kart\u0119");
		btnUsuKart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_nrKarty.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Nie wpisano nr karty");
				}else{
					Statement st = null;
					ResultSet rs = null;
					try {
						st = connection.createStatement();
						rs = st.executeQuery("SELECT id_karty, nazwisko_imie, HacoSoftnumber, stanowisko, firma FROM cards_name_surname_nrHacoSoft USE INDEX (idx_id_karty) WHERE id_karty='"+textField_nrKarty.getText()+"'");
						if(rs.next()){
							int YesNo = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usun�� kart�?", "Usuni�cie karty", JOptionPane.YES_NO_OPTION);
							if(YesNo == JOptionPane.YES_OPTION){
								st.close();
								rs.close();
								PreparedStatement pst =null;
								try{
									String query="DELETE FROM cards_name_surname_nrHacoSoft WHERE id_karty="+textField_nrKarty.getText()+"";
									pst=connection.prepareStatement(query);
									pst.execute();
									pst.close();
									textField_nrKarty.setText("");
									textField_nazwImie.setText("");
									textField_nrHacoSoft.setText("");
									textField_stanowisko.setText("");
									textField_firma.setText("");
								}catch(SQLException e3){
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Sprobuj jeszcze raz - baza jest zajeta");
									e3.printStackTrace();
								}
							}else{
								//nic :)
							}
						}else{
							JOptionPane.showMessageDialog(null, "Nie ma takiego nr karty w bazie");
						}
						st.close();
						rs.close();
					}catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		btnUsuKart.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		textField_nrKarty.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					if(textField_nrKarty.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Nie wpisano nr karty");
					}else{
						Statement st = null;
						ResultSet rs = null;
						try {
							st = connection.createStatement();
							rs = st.executeQuery("SELECT id_karty, nazwisko_imie, HacoSoftnumber, stanowisko, firma FROM cards_name_surname_nrHacoSoft USE INDEX (idx_id_karty) WHERE id_karty='"+textField_nrKarty.getText()+"'");
							if(rs.next()){
								textField_nazwImie.setText(rs.getString("nazwisko_imie"));
								textField_nrHacoSoft.setText(rs.getString("HacoSoftnumber"));
								textField_stanowisko.setText(rs.getString("stanowisko"));
								textField_firma.setText(rs.getString("firma"));
								st.close();
								rs.close();
							}else{
								JOptionPane.showMessageDialog(null, "Nie ma takiego nr karty w bazie");
							}
							st.close();
							rs.close();
						}catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
			}
		});
		
		btnArchiwizujKarte = new JButton("Archiwizuj karte");
		btnArchiwizujKarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="SELECT id_karty, nazwisko_imie, HacoSoftnumber, stanowisko, firma FROM cards_name_surname_nrhacosoft WHERE id_karty='"+textField_nrKarty.getText()+"' AND nazwisko_imie='"+textField_nazwImie.getText()+"' AND HacoSoftnumber='"+textField_nrHacoSoft.getText()+"'";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					if(rs.next()){
						// kasuje karte z cards_name_surname_nrHacosoft
						try {
							String query2="DELETE FROM cards_name_surname_nrhacosoft WHERE id_karty='"+textField_nrKarty.getText()+"' AND nazwisko_imie='"+textField_nazwImie.getText()+"' AND HacoSoftnumber='"+textField_nrHacoSoft.getText()+"'";
							PreparedStatement pst2=connection.prepareStatement(query2);
							pst2.executeQuery();
							pst2.close();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						// wpisuje karte do tabeli archiwum
						try {
							String query3="INSERT INTO cardsarchive (id_karty, nazwisko_imie, HacoSoftnumber, stanowisko, firma) VALUES ('"+textField_nrKarty.getText()+"', '"+textField_nazwImie.getText()+"', '"+textField_nrHacoSoft.getText()+"', '"+textField_stanowisko.getText()+"', '"+textField_firma.getText()+"')";
							PreparedStatement pst3=connection.prepareStatement(query3);
							pst3.executeQuery();
							pst3.close();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Zarchiwizowano karte nr: "+textField_nrKarty.getText()+" dla pracownika: "+textField_nazwImie.getText());
						textField_nrKarty.setText("");
						textField_nazwImie.setText("");
						textField_nrHacoSoft.setText("");
					}else{
						JOptionPane.showMessageDialog(null, "Nie ma takiej karty w bazie kart aktywnych");
					}
					pst.close();
					rs.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnArchiwizujKarte.setToolTipText("przenosi karte do archiwum (wszystkie pola musza byc wypelnione, zeby archiwizowac karte)");
		btnArchiwizujKarte.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel = new JLabel("<html> Je\u015Bli pracownik nie ma przypisanego nr z HacoSofta, w pole nr z HacoSofta wprowadzamy warto\u015B\u0107 0 </html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addComponent(lblMojeMenu, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txt1, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addGap(0)
					.addComponent(textField_nrKarty, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txt2, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addComponent(textField_nazwImie, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txt3, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addComponent(textField_nrHacoSoft, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txt4, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addComponent(textField_firma, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(txt5, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addComponent(textField_stanowisko, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(120)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnArchiwizujKarte, GroupLayout.PREFERRED_SIZE, 217, Short.MAX_VALUE)
						.addComponent(btnSprawdzDaneKarty, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
						.addComponent(btnUsuKart, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
						.addComponent(btnNowaKarta, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
					.addGap(104))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(40)
					.addComponent(lblMojeMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txt1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_nrKarty, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txt2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_nazwImie, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txt3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_nrHacoSoft, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txt4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_firma, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txt5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_stanowisko, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnNowaKarta, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnUsuKart, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSprawdzDaneKarty, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnArchiwizujKarte, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);	
		
		}
	}
