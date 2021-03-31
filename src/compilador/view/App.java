package compilador.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;

import compilador.services.AppViewService;
import compilador.services.ToolbarService;

public class App {

	private ToolbarService toolbarService = new ToolbarService();
	private AppViewService service = new AppViewService();
	
	private JFrame frame;
	private JTextField tfStatusBar;
	//Tamanho minimo da janela da aplicao
	private static final Dimension MINIMUM_FRAME_DIMENSION = new Dimension(900, 600);
	//Determina o tamanho dos botoes da toolbar
	private static final Dimension BUTTON_DIMENSION = new Dimension(168, 60);
	//Determina a cor dos botoes da toolbar
	private static final Color BUTTON_COLOR = Color.WHITE;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(MINIMUM_FRAME_DIMENSION);
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JPanel panelToolBar = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelToolBar, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panelToolBar, 10, SpringLayout.WEST, frame.getContentPane());
		panelToolBar.setBackground(new Color(238, 232, 170));
		frame.getContentPane().add(panelToolBar);
		panelToolBar.setLayout(new BoxLayout(panelToolBar, BoxLayout.Y_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		springLayout.putConstraint(SpringLayout.EAST, panelToolBar, -6, SpringLayout.WEST, splitPane);
		springLayout.putConstraint(SpringLayout.WEST, splitPane, 184, SpringLayout.WEST, frame.getContentPane());
		splitPane.setDividerLocation(380);
		springLayout.putConstraint(SpringLayout.EAST, splitPane, -10, SpringLayout.EAST, frame.getContentPane());
		
		JScrollPane spMessage = new JScrollPane();
		spMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spMessage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		splitPane.setRightComponent(spMessage);
		
		JTextArea taMessage = new JTextArea();
		taMessage.setEditable(false);
		spMessage.setViewportView(taMessage);

		tfStatusBar = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, panelToolBar, -6, SpringLayout.NORTH, tfStatusBar);
		springLayout.putConstraint(SpringLayout.WEST, tfStatusBar, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tfStatusBar, -10, SpringLayout.EAST, frame.getContentPane());
		tfStatusBar.setBackground(new Color(211, 211, 211));
		springLayout.putConstraint(SpringLayout.NORTH, tfStatusBar, -20, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tfStatusBar, 0, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(tfStatusBar);
		tfStatusBar.setColumns(10);

		JScrollPane spEditor = new JScrollPane();
		spEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		splitPane.setLeftComponent(spEditor);
		
		JTextArea taEditor = new JTextArea();
		spEditor.setViewportView(taEditor);
		taEditor.setBorder(new NumberedBorder());
		
		Action actionNovo = new AbstractAction("novo [ctrl-n]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent e) {
				List<JTextComponent> list = Arrays.asList(taEditor, taMessage, tfStatusBar);
				for(JTextComponent tc : list)
					toolbarService.cleanText(tc);
		    }
		};
		JButton btnNovo = new JButton(actionNovo);
		btnNovo.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-novo.png")));
		panelToolBar.add(btnNovo);
		service.configButtonShortcut(btnNovo, "control N");
		
		Action actionAbrir = new AbstractAction("abrir [ctrl-o]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				try {
					String filePath = toolbarService.loadFile(taEditor, "txt");
					if (filePath != null) {
						toolbarService.cleanText(taMessage);
						tfStatusBar.setText(filePath);
					}
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, e.getMessage());
				}
		    }
		};
		JButton btnAbrir = new JButton(actionAbrir);
		btnAbrir.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-abrir.png")));
		panelToolBar.add(btnAbrir);
		service.configButtonShortcut(btnAbrir, "control O");
		
		Action actionSalvar = new AbstractAction("salvar [ctrl-s]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				String text = taEditor.getText();
				try {
					String newFilePath = toolbarService.saveFile(text, "txt");
					tfStatusBar.setText(newFilePath);
					toolbarService.cleanText(taMessage);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, e.getMessage());
				}
		    }
		};
		JButton btnSalvar = new JButton(actionSalvar);
		btnSalvar.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-salvar.png")));
		panelToolBar.add(btnSalvar);
		service.configButtonShortcut(btnSalvar, "control S");
		
		Action actionCopiar = new AbstractAction("copiar [ctrl-c]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				toolbarService.copyTextToClipBoard(taEditor.getText());
			}
		};
		JButton btnCopiar = new JButton(actionCopiar);
		btnCopiar.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-copiar.png")));
		panelToolBar.add(btnCopiar);
		service.configButtonShortcut(btnCopiar, "control C");
		
		Action actionColar = new AbstractAction("colar [ctrl-v]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				toolbarService.pasteToTextComponent(taEditor);
			}
		};
		JButton btnColar = new JButton(actionColar);
		btnColar.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-colar.png")));
		panelToolBar.add(btnColar);
		service.configButtonShortcut(btnColar, "control V");
		
		Action actionRecortar = new AbstractAction("recortar [ctrl-x]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				toolbarService.cutFromTextComponent(taEditor);
			}
		};
		JButton btnRecortar = new JButton(actionRecortar);
		btnRecortar.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-recortar.png")));
		panelToolBar.add(btnRecortar);
		service.configButtonShortcut(btnRecortar, "control X");
		
		Action actionCompilar = new AbstractAction("compilar [F7]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				taMessage.setText("compilação de programas ainda não foi implementada");
			}
		};
		JButton btnCompilar = new JButton(actionCompilar);
		btnCompilar.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-compilar.png")));
		panelToolBar.add(btnCompilar);
		service.configButtonShortcut(btnCompilar, "F7");
		
		Action actionEquipe = new AbstractAction("equipe [F1]") {
			private static final long serialVersionUID = 1L;
			@Override
		    public void actionPerformed(ActionEvent event) {
				taMessage.setText(toolbarService.getTeam());
			}
		};
		JButton btnEquipe = new JButton(actionEquipe);
		btnEquipe.setIcon(new ImageIcon(App.class.getResource("/compilador/assets/icon-equipe.png")));
		panelToolBar.add(btnEquipe);
		service.configButtonShortcut(btnEquipe, "F1");
		
		springLayout.putConstraint(SpringLayout.NORTH, splitPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, splitPane, -7, SpringLayout.NORTH, tfStatusBar);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane);

		Arrays.asList(panelToolBar.getComponents()).stream().map(b -> service.configToolBarButton(b, BUTTON_DIMENSION, BUTTON_COLOR)).collect(Collectors.toList());
	}
	
}
