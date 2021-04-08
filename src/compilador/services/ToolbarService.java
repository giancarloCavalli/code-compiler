package compilador.services;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import compilador.services.gals.LexicalError;
import compilador.services.gals.Lexico;
import compilador.services.gals.Token;

public class ToolbarService {

	public ToolbarService() {
	}

	public void cleanText(JTextComponent textComponent) {
		textComponent.setText("");
	}

	// Se salvar arquivo, retorna o absolut path. Se cancelar no meio do salvamento,
	// retorna null
	public String saveFile(String dataToBeSaved, String fileType) throws RuntimeException, IOException {
		String filePath = "";
		if (dataToBeSaved.isBlank() || dataToBeSaved.isEmpty())
			throw new RuntimeException("Não há dados a serem salvos");
		else {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, fileType);
			chooser.setFileFilter(filter);
			int retVal = chooser.showSaveDialog(null);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				filePath = chooser.getSelectedFile().getAbsoluteFile().toString() + "." + fileType;
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
					bw.write(dataToBeSaved);
					bw.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					throw new IOException("Erro ao salvar arquivo");
				}
			} else {
				return null;
			}
		}
		return filePath;
	}

	public String loadFile(JTextComponent textComponent, String fileType) throws IOException {
		String filePath;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, fileType);
		chooser.setFileFilter(filter);
		int retVal = chooser.showOpenDialog(null);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			filePath = chooser.getSelectedFile().getAbsolutePath().toString();
			try {
				textComponent.setText(readFile(chooser.getSelectedFile()));
			} catch (IOException ioe) {
				throw new IOException(ioe.getMessage());
			}
		} else {
			return null;
		}
		return filePath;
	}

	private String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		try {
			String str = br.readLine();
			if (str != null)
				sb.append(str);
			str = br.readLine();

			while (str != null) {
				sb.append("\n" + str);
				str = br.readLine();
			}
		} catch (EOFException eofe) {

		} catch (IOException ioe) {
			br.close();
			throw new IOException("Erro ao ler arquivo");
		}
		br.close();
		return String.valueOf(sb);
	}

	public String getTeam() {
		return "Team:" + "\nGiancarlo Cavalli" + "\nJardel Pereira Zermiani" + "\nThomas Michels Rodrigues";
	}
	
	public void copyTextToClipBoard(String text) {
		Toolkit.getDefaultToolkit()
        .getSystemClipboard()
        .setContents(new StringSelection(text), null);
	}

	public void pasteToTextComponent(JTextComponent textComponent){
	    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
	    Transferable t = c.getContents(this);
	    if (t == null)
	        return;
	    try {
	    	textComponent.setText(textComponent.getText().concat((String) t.getTransferData(DataFlavor.stringFlavor)));
	    } catch (Exception e){
	        e.printStackTrace();
	    }
	}
	
	public void cutFromTextComponent(JTextComponent textComponent) {
		copyTextToClipBoard(textComponent.getText());
		cleanText(textComponent);
	}
	
	public int getTokenLine(String input, int position) {
		int line = 1;
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(input.substring(0, position));
		while (matcher.find()) {
			line++;
		}
		return line;
	}
	
	public String compileCode(String input) {
		StringBuilder compileMsg = new StringBuilder();
		Lexico lexico = new Lexico(input);
		
		try {
			Token t = null;
			compileMsg.append("linha\tclasse\t\tlexema\n");
			while ((t = lexico.nextToken()) != null) {
				compileMsg.append(getTokenLine(input, t.getPosition()) + "\t" + t.getId() + "\t\t" + t.getLexeme() + "\n");
			}
			compileMsg.append("\nprograma compilado com sucesso");
		} catch (LexicalError e) {
			compileMsg = new StringBuilder();
			if (e.getMessage().equals("símbolo inválido"))
				compileMsg.append("Erro na linha " + getTokenLine(input, e.getPosition()) + " - " + input.charAt(e.getPosition()) + " " + e.getMessage());
			else
				compileMsg.append("Erro na linha " + getTokenLine(input, e.getPosition()) + " - " + e.getMessage());
		}
		
		return String.valueOf(compileMsg);
	}

}
