package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.JRadioButton;

public class FrameAlineacionManual extends JFrame {

	private String rutaCanalGRE = null;
	private String rutaCanalNIR = null;
	private String rutaCanalRED = null;
	private String rutaCanalREG = null;
	private int horizontalOffsetGRE = 0;
	private int horizontalOffsetNIR = 0;
	private int horizontalOffsetRED = 0;
	private int horizontalOffsetREG = 0;
	private int verticalOffsetGRE = 0;
	private int verticalOffsetNIR = 0;
	private int verticalOffsetRED = 0;
	private int verticalOffsetREG = 0;
	
	private JPanel contentPane;
	private JLabel labelFoto;
	private JLabel labelHorizontalOffsetGRE;
	private JLabel labelHorizontalOffsetRED;
	private JLabel labelHorizontalOffsetREG;
	private JLabel labelVerticalOffsetGRE;
	private JLabel labelVerticalOffsetRED;
	private JLabel labelVerticalOffsetNIR;
	private JLabel labelVerticalOffsetREG;
	private JLabel labelHorizontalOffsetNIR;
	private JButton buttonGRE;
	private JButton buttonNIR;
	private JButton buttonRED;
	private JButton buttonREG;
	private JCheckBox checkboxREG;
	private JCheckBox checkboxGRE;
	private JCheckBox checkboxNIR;
	private JCheckBox checkboxRED;
	private JRadioButton radioButtonZoom1;
	private JRadioButton radioButtonZoom05;
	private JRadioButton radioButtonZoom3;
	
	public static void main(String[] args) {
		try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);

    		// cargo opencv
            String opencvPath = prop.getProperty("opencv_path");
    		System.load(opencvPath);
    		EventQueue.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					FrameAlineacionManual frame = new FrameAlineacionManual();
    					frame.setVisible(true);
    					frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		});
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	
	public FrameAlineacionManual() throws IOException {
		setTitle("Alinear Canales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1278, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 140));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		checkboxGRE = new JCheckBox("GRE");
		checkboxGRE.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				actualizarPantalla();
			}
		});
		checkboxGRE.setEnabled(false);
		checkboxGRE.setBounds(8, 9, 60, 26);
		panel.add(checkboxGRE);
		
		checkboxNIR = new JCheckBox("NIR");
		checkboxNIR.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				actualizarPantalla();
			}
		});
		checkboxNIR.setEnabled(false);
		checkboxNIR.setBounds(8, 40, 60, 26);
		panel.add(checkboxNIR);
		
		checkboxRED = new JCheckBox("RED");
		checkboxRED.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				actualizarPantalla();
			}
		});
		checkboxRED.setEnabled(false);
		checkboxRED.setBounds(8, 71, 60, 26);
		panel.add(checkboxRED);
		
		checkboxREG = new JCheckBox("REG");
		checkboxREG.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				actualizarPantalla();
			}
		});
		checkboxREG.setEnabled(false);
		checkboxREG.setBounds(8, 102, 60, 26);
		panel.add(checkboxREG);
		
		buttonGRE = new JButton("");
		buttonGRE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					rutaCanalGRE = selectedFile.getAbsolutePath();
					actualizarPantalla();
				}
			}
		});
		buttonGRE.setBounds(76, 9, 490, 26);
		panel.add(buttonGRE);
		
		buttonNIR = new JButton("");
		buttonNIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					rutaCanalNIR = selectedFile.getAbsolutePath();
					actualizarPantalla();
				}
			}
		});
		buttonNIR.setBounds(76, 41, 490, 26);
		panel.add(buttonNIR);
		
		buttonRED = new JButton("");
		buttonRED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					rutaCanalRED = selectedFile.getAbsolutePath();
					actualizarPantalla();
				}
			}
		});
		buttonRED.setBounds(76, 72, 490, 26);
		panel.add(buttonRED);
		
		buttonREG = new JButton("");
		buttonREG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					rutaCanalREG = selectedFile.getAbsolutePath();
					actualizarPantalla();
				}
			}
		});
		buttonREG.setBounds(76, 103, 490, 26);
		panel.add(buttonREG);
		
		JButton buttonBajarHorizontalOffsetGRE = new JButton("<-");
		buttonBajarHorizontalOffsetGRE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetGRE--;
				actualizarPantalla();
			}
		});
		buttonBajarHorizontalOffsetGRE.setBounds(603, 10, 50, 25);
		panel.add(buttonBajarHorizontalOffsetGRE);
		
		JButton buttonBajarHorizontalOffsetNIR = new JButton("<-");
		buttonBajarHorizontalOffsetNIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetNIR--;
				actualizarPantalla();
			}
		});
		buttonBajarHorizontalOffsetNIR.setBounds(603, 41, 50, 25);
		panel.add(buttonBajarHorizontalOffsetNIR);
		
		JButton buttonBajarHorizontalOffsetRED = new JButton("<-");
		buttonBajarHorizontalOffsetRED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetRED--;
				actualizarPantalla();
			}
		});
		buttonBajarHorizontalOffsetRED.setBounds(603, 72, 50, 25);
		panel.add(buttonBajarHorizontalOffsetRED);
		
		JButton buttonBajarHorizontalOffsetREG = new JButton("<-");
		buttonBajarHorizontalOffsetREG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetREG--;
				actualizarPantalla();
			}
		});
		buttonBajarHorizontalOffsetREG.setBounds(603, 103, 50, 25);
		panel.add(buttonBajarHorizontalOffsetREG);
		
		JButton buttonSubirHorizontalOffsetGRE = new JButton("->");
		buttonSubirHorizontalOffsetGRE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetGRE++;
				actualizarPantalla();
			}
		});
		buttonSubirHorizontalOffsetGRE.setBounds(727, 10, 50, 25);
		panel.add(buttonSubirHorizontalOffsetGRE);
		
		labelHorizontalOffsetGRE = new JLabel("");
		labelHorizontalOffsetGRE.setHorizontalAlignment(SwingConstants.CENTER);
		labelHorizontalOffsetGRE.setBounds(665, 9, 50, 26);
		panel.add(labelHorizontalOffsetGRE);
		
		labelHorizontalOffsetNIR = new JLabel("");
		labelHorizontalOffsetNIR.setHorizontalAlignment(SwingConstants.CENTER);
		labelHorizontalOffsetNIR.setBounds(665, 40, 50, 26);
		panel.add(labelHorizontalOffsetNIR);
		
		JButton buttonSubirHorizontalOffsetNIR = new JButton("->");
		buttonSubirHorizontalOffsetNIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetNIR++;
				actualizarPantalla();
			}
		});
		buttonSubirHorizontalOffsetNIR.setBounds(727, 41, 50, 25);
		panel.add(buttonSubirHorizontalOffsetNIR);
		
		labelHorizontalOffsetRED = new JLabel("");
		labelHorizontalOffsetRED.setHorizontalAlignment(SwingConstants.CENTER);
		labelHorizontalOffsetRED.setBounds(665, 71, 50, 26);
		panel.add(labelHorizontalOffsetRED);
		
		JButton buttonSubirHorizontalOffsetRED = new JButton("->");
		buttonSubirHorizontalOffsetRED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetRED++;
				actualizarPantalla();
			}
		});
		buttonSubirHorizontalOffsetRED.setBounds(727, 72, 50, 25);
		panel.add(buttonSubirHorizontalOffsetRED);
		
		labelHorizontalOffsetREG = new JLabel("");
		labelHorizontalOffsetREG.setHorizontalAlignment(SwingConstants.CENTER);
		labelHorizontalOffsetREG.setBounds(665, 102, 50, 26);
		panel.add(labelHorizontalOffsetREG);
		
		JButton buttonSubirHorizontalOffsetREG = new JButton("->");
		buttonSubirHorizontalOffsetREG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalOffsetREG++;
				actualizarPantalla();
			}
		});
		buttonSubirHorizontalOffsetREG.setBounds(727, 103, 50, 25);
		panel.add(buttonSubirHorizontalOffsetREG);
		
		JButton buttonSubirVerticalOffsetGRE = new JButton("\u0245");
		buttonSubirVerticalOffsetGRE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetGRE++;
				actualizarPantalla();
			}
		});
		buttonSubirVerticalOffsetGRE.setBounds(938, 10, 50, 25);
		panel.add(buttonSubirVerticalOffsetGRE);
		
		labelVerticalOffsetGRE = new JLabel("");
		labelVerticalOffsetGRE.setHorizontalAlignment(SwingConstants.CENTER);
		labelVerticalOffsetGRE.setBounds(876, 9, 50, 26);
		panel.add(labelVerticalOffsetGRE);
		
		JButton buttonBajarVerticalOffsetGRE = new JButton("V");
		buttonBajarVerticalOffsetGRE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetGRE--;
				actualizarPantalla();
			}
		});
		buttonBajarVerticalOffsetGRE.setBounds(814, 10, 50, 25);
		panel.add(buttonBajarVerticalOffsetGRE);
		
		JButton buttonSubirVerticalOffsetNIR = new JButton("\u0245");
		buttonSubirVerticalOffsetNIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetNIR++;
				actualizarPantalla();
			}
		});
		buttonSubirVerticalOffsetNIR.setBounds(938, 42, 50, 25);
		panel.add(buttonSubirVerticalOffsetNIR);
		
		labelVerticalOffsetNIR = new JLabel("");
		labelVerticalOffsetNIR.setHorizontalAlignment(SwingConstants.CENTER);
		labelVerticalOffsetNIR.setBounds(876, 41, 50, 26);
		panel.add(labelVerticalOffsetNIR);
		
		JButton buttonBajarVerticalOffsetNIR = new JButton("V");
		buttonBajarVerticalOffsetNIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetNIR--;
				actualizarPantalla();
			}
		});
		buttonBajarVerticalOffsetNIR.setBounds(814, 42, 50, 25);
		panel.add(buttonBajarVerticalOffsetNIR);
		
		JButton buttonSubirVerticalOffsetRED = new JButton("\u0245");
		buttonSubirVerticalOffsetRED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetRED++;
				actualizarPantalla();
			}
		});
		buttonSubirVerticalOffsetRED.setBounds(938, 72, 50, 25);
		panel.add(buttonSubirVerticalOffsetRED);
		
		labelVerticalOffsetRED = new JLabel("");
		labelVerticalOffsetRED.setHorizontalAlignment(SwingConstants.CENTER);
		labelVerticalOffsetRED.setBounds(876, 71, 50, 26);
		panel.add(labelVerticalOffsetRED);
		
		JButton buttonBajarVerticalOffsetRED = new JButton("V");
		buttonBajarVerticalOffsetRED.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetRED--;
				actualizarPantalla();
			}
		});
		buttonBajarVerticalOffsetRED.setBounds(814, 72, 50, 25);
		panel.add(buttonBajarVerticalOffsetRED);
		
		JButton buttonSubirVerticalOffsetREG = new JButton("\u0245");
		buttonSubirVerticalOffsetREG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetREG++;
				actualizarPantalla();
			}
		});
		buttonSubirVerticalOffsetREG.setBounds(938, 103, 50, 25);
		panel.add(buttonSubirVerticalOffsetREG);
		
		labelVerticalOffsetREG = new JLabel("");
		labelVerticalOffsetREG.setHorizontalAlignment(SwingConstants.CENTER);
		labelVerticalOffsetREG.setBounds(876, 102, 50, 26);
		panel.add(labelVerticalOffsetREG);
		
		JButton buttonBajarVerticalOffsetREG = new JButton("V");
		buttonBajarVerticalOffsetREG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalOffsetREG--;
				actualizarPantalla();
			}
		});
		buttonBajarVerticalOffsetREG.setBounds(814, 103, 50, 25);
		panel.add(buttonBajarVerticalOffsetREG);
		
		JLabel lblZoom = new JLabel("ZOOM");
		lblZoom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblZoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblZoom.setBounds(1045, 9, 93, 26);
		panel.add(lblZoom);
		
		radioButtonZoom1 = new JRadioButton("1");
		radioButtonZoom1.setSelected(true);
		radioButtonZoom1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					actualizarPantalla();
				}
			}
		});
		radioButtonZoom1.setBounds(1045, 71, 93, 26);
		panel.add(radioButtonZoom1);
		
		radioButtonZoom05 = new JRadioButton("0.5");
		radioButtonZoom05.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					actualizarPantalla();
				}
			}
		});
		radioButtonZoom05.setBounds(1045, 40, 93, 26);
		panel.add(radioButtonZoom05);
		
		radioButtonZoom3 = new JRadioButton("3");
		radioButtonZoom3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					actualizarPantalla();
				}
			}
		});
		radioButtonZoom3.setBounds(1045, 103, 93, 26);
		panel.add(radioButtonZoom3);
		
		ButtonGroup buttonGroupZoom = new ButtonGroup();
		buttonGroupZoom.add(radioButtonZoom1);
		buttonGroupZoom.add(radioButtonZoom05);
		buttonGroupZoom.add(radioButtonZoom3);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		labelFoto = new JLabel("");
		scrollPane.setViewportView(labelFoto);
		
		actualizarPantalla();
	}
	
	
	private void actualizarPantalla(){
		actualizarOffsets();
		actualizarButtonsTexts();
		actualizarImagen();
		habilitarCheckboxes();
	}
	
	private void actualizarOffsets(){
		labelHorizontalOffsetGRE.setText(horizontalOffsetGRE + "");
		labelHorizontalOffsetNIR.setText(horizontalOffsetNIR + "");
		labelHorizontalOffsetRED.setText(horizontalOffsetRED + "");
		labelHorizontalOffsetREG.setText(horizontalOffsetREG + "");
		labelVerticalOffsetGRE.setText(verticalOffsetGRE + "");
		labelVerticalOffsetNIR.setText(verticalOffsetNIR + "");
		labelVerticalOffsetRED.setText(verticalOffsetRED + "");
		labelVerticalOffsetREG.setText(verticalOffsetREG + "");
	}
	
	private void actualizarButtonsTexts(){
		if(rutaCanalGRE == null){
			buttonGRE.setText("Cargar Imagen");
		}else{
			buttonGRE.setText(rutaCanalGRE);
		}
		if(rutaCanalNIR == null){
			buttonNIR.setText("Cargar Imagen");
		}else{
			buttonNIR.setText(rutaCanalNIR);
		}
		if(rutaCanalRED == null){
			buttonRED.setText("Cargar Imagen");
		}else{
			buttonRED.setText(rutaCanalRED);
		}
		if(rutaCanalREG == null){
			buttonREG.setText("Cargar Imagen");
		}else{
			buttonREG.setText(rutaCanalREG);
		}
	}
	
	private void habilitarCheckboxes(){
		checkboxGRE.setEnabled(rutaCanalGRE != null);
		checkboxNIR.setEnabled(rutaCanalNIR != null);
		checkboxRED.setEnabled(rutaCanalRED != null);
		checkboxREG.setEnabled(rutaCanalREG != null);
	}
	
	private void actualizarImagen(){
		Mat matGRE = null;
		if(checkboxGRE.isEnabled() && checkboxGRE.isSelected()){
			matGRE = Imgcodecs.imread(rutaCanalGRE);
			matGRE = getChannel(matGRE, 0);
		}
		Mat matNIR = null;
		if(checkboxNIR.isEnabled() && checkboxNIR.isSelected()){
			matNIR = Imgcodecs.imread(rutaCanalNIR);
			matNIR = getChannel(matNIR, 0);
		}
		Mat matRED = null;
		if(checkboxRED.isEnabled() && checkboxRED.isSelected()){
			matRED = Imgcodecs.imread(rutaCanalRED);
			matRED = getChannel(matRED, 0);
		}
		Mat matREG = null;
		if(checkboxREG.isEnabled() && checkboxREG.isSelected()){
			matREG = Imgcodecs.imread(rutaCanalREG);
			matREG = getChannel(matREG, 0);
		}
		// si todos los canales estan en null, no muestro nada
		if(matGRE == null && matNIR == null && matRED == null && matREG == null){
			labelFoto.setIcon(null);
			return;
		}
		// si todos los canales son distintos a null, tiro un error ya que no se pueden mostrar 4 canales a la vez
		if(matGRE != null && matNIR != null && matRED != null && matREG != null){
			JOptionPane.showMessageDialog(null, "No se puede habilitar los 4 canales a la vez");
			System.exit(0);
		}
		// los canales vienen rotados 180 grados
		matGRE = rotateImage90(rotateImage90(matGRE));
		matNIR = rotateImage90(rotateImage90(matNIR));
		matRED = rotateImage90(rotateImage90(matRED));
		matREG = rotateImage90(rotateImage90(matREG));
		
		// aplico el offset a cada uno de los canales, agregando filas y columnas con ceros
		matGRE = aplicarOffset(matGRE, horizontalOffsetGRE, verticalOffsetGRE);
		matNIR = aplicarOffset(matNIR, horizontalOffsetNIR, verticalOffsetNIR);
		matRED = aplicarOffset(matRED, horizontalOffsetRED, verticalOffsetRED);
		matREG = aplicarOffset(matREG, horizontalOffsetREG, verticalOffsetREG);
		// armo una sola imagen combinando los canales
		List<Mat> canales = new ArrayList<>();
		if(matGRE != null) canales.add(matGRE);
		if(matNIR != null) canales.add(matNIR);
		if(matRED != null) canales.add(matRED);
		if(matREG != null) canales.add(matREG);
		// si no llego a los 3 canales, completo con canales vacios
		while(canales.size() < 3){
			Mat matVacia = new Mat(canales.get(0).size(), canales.get(0).type(), Scalar.all(0));
			matVacia = getChannel(matVacia, 0);
			canales.add(matVacia);
		}
		// creo el mat combinando los 3 canales
		Mat mat = new Mat();
		Core.merge(canales, mat);
		// aplico el zoom
		mat = aplicarZoom(mat);
		// muestro el mat en el label
		Image bufferedImage = null;
		try{
			bufferedImage = HighGui.toBufferedImage(mat);
		}catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
			System.exit(0);
		}
		ImageIcon imageIcon = new ImageIcon(bufferedImage);
		labelFoto.setIcon(imageIcon);
	}
	
	private Mat aplicarZoom(Mat mat){
		if(mat == null) return null;
		Mat retorno = new Mat();
		Core.copyTo(mat, retorno, new Mat());
		double zoom = 1;
		if(radioButtonZoom05.isSelected()){
			zoom = 0.5;
		}else if(radioButtonZoom3.isSelected()){
			zoom = 3;
		}
		if(zoom == 1){
			return retorno;
		}
		return resizeImageByRatio(retorno, zoom);
	}
	
	private Mat aplicarOffset(Mat mat, int horizontalOffset, int verticalOffset){
		if(mat == null) return null;
		Mat retorno = new Mat();
		Core.copyTo(mat, retorno, new Mat());
		Mat matHorOffset = null;
		if(horizontalOffset != 0){
			matHorOffset = new Mat(new int[]{retorno.rows(), Math.abs(horizontalOffset)}, retorno.type(), Scalar.all(0));
		}
		Mat matVerOffset = null;
		if(verticalOffset != 0){
			matVerOffset = new Mat(new int[]{Math.abs(verticalOffset), retorno.cols()}, retorno.type(), Scalar.all(0));
		}
		if(matHorOffset != null){
			// si el horizontalOffset es negativo, matHorOffset debe ser agregado a la der
			if(horizontalOffset < 0){
				Core.hconcat(Arrays.asList(retorno, matHorOffset), retorno);
				retorno = retorno.colRange(Math.abs(horizontalOffset), retorno.cols());
			}
			// si el horizontalOffset es positivo, matHorOffset debe ser agregado a la izq
			if(horizontalOffset > 0){
				Core.hconcat(Arrays.asList(matHorOffset, retorno), retorno);
				retorno = retorno.colRange(0, retorno.cols() - Math.abs(horizontalOffset));
			}
		}
		if(matVerOffset != null){
			// si el verticalOffset es negativo, matVerOffset debe ser agregado arriba
			if(verticalOffset < 0){
				Core.vconcat(Arrays.asList(matVerOffset, retorno), retorno);
				retorno = retorno.rowRange(0, retorno.rows() - Math.abs(verticalOffset));
			}
			// si el verticalOffset es positivo, matVerOffset debe ser agregado abajo
			if(verticalOffset > 0){
				Core.vconcat(Arrays.asList(retorno, matVerOffset), retorno);
				retorno = retorno.rowRange(Math.abs(verticalOffset), retorno.rows());
			}
		}
		return retorno;
	}
	
	private Mat getChannel(Mat mat, int channelIndex){
		List<Mat> channels = new ArrayList<Mat>();
		Core.split(mat, channels);
		return channels.get(channelIndex);
	}
	
	private Mat resizeImageByRatio(Mat image, double resizingRatio){
		Mat retorno = new Mat();
		Size newSize = new Size(Math.round((double)image.width()*resizingRatio), Math.round((double)image.height()*resizingRatio));
		Imgproc.resize(image, retorno, newSize);
		return retorno;
	}
	
	private Mat rotateImage90(Mat image){
		if(image == null) return null;
		Mat ret = new Mat();
		Core.transpose(image, ret);
		Core.flip(ret, ret, 1);
		return ret;
	}
}
