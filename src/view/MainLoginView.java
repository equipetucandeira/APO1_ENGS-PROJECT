package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import model.Advisor;
import model.Student;
import model.User;

public class MainLoginView {

	public static void main(String[] args) {
		Display display = new Display();
		createLoginModal(display);
	}

	private static void createLoginModal(Display display) {

		Shell loginShell = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		loginShell.setText("Selecione o Tipo de Login");
		loginShell.setLayout(new GridLayout(2, false));

		Label label = new Label(loginShell, SWT.NONE);
		label.setText("Selecione o tipo de login:");
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		label.setLayoutData(gridData);

		Button professorButton = new Button(loginShell, SWT.PUSH);
		professorButton.setText("Login Professor");
		professorButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		Button studentButton = new Button(loginShell, SWT.PUSH);
		studentButton.setText("Login Aluno");
		studentButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		professorButton.addListener(SWT.Selection, e -> openLoginScreen(loginShell, "Login Professor", new Advisor()));
		studentButton.addListener(SWT.Selection, e -> openLoginScreen(loginShell, "Login Aluno", new Student()));

		loginShell.pack();
		loginShell.open();

		while (!loginShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void openLoginScreen(Shell loginShell, String title, User user) {
		for (Control control : loginShell.getChildren()) {
			control.dispose();
		}

		loginShell.setText("Acessar");
		loginShell.setLayout(new GridLayout(2, false));

		GridLayout gl_loginShell = new GridLayout(2, false);
		gl_loginShell.horizontalSpacing = 40;
		gl_loginShell.marginWidth = 30;
		gl_loginShell.marginTop = 30;
		gl_loginShell.marginRight = 30;
		gl_loginShell.marginLeft = 30;
		gl_loginShell.marginHeight = 30;
		gl_loginShell.marginBottom = 30;
		loginShell.setLayout(gl_loginShell);

		Label userLabel = new Label(loginShell, SWT.NONE);
		userLabel.setText("email:");
		Text userText = new Text(loginShell, SWT.BORDER);

		GridData gd_userText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_userText.widthHint = 150;
		userText.setLayoutData(gd_userText);

		Label passLabel = new Label(loginShell, SWT.NONE);
		passLabel.setText("Senha:");
		Text passText = new Text(loginShell, SWT.BORDER | SWT.PASSWORD);
		GridData gd_passText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_passText.widthHint = 150;
		passText.setLayoutData(gd_passText);

		Button loginButton = new Button(loginShell, SWT.PUSH);
		loginButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		loginButton.setText("Login");

		loginButton.addListener(SWT.Selection, e -> {

			String email = userText.getText();
			String password = passText.getText();

			try {
				user.setEmail(email);
				user.setPassword(password);
				user.login();
				loginShell.dispose();
				
				if(user.getClass().equals(Advisor.class)){
					AdvisorMenuView window = new AdvisorMenuView(Display.getDefault());
					window.open((Advisor) user);
				}else {
					StudentMenuView window = new StudentMenuView(Display.getDefault());
					window.open((Student) user);
				}
				

			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}

		});

		loginShell.pack();
		loginShell.open();
	}

}
