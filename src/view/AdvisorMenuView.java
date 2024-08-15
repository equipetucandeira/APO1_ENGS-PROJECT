package view;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.ibm.icu.util.Calendar;

import model.Advisor;
import model.Advisor;
import model.InterfaceProject;
import model.Student;
import model.Task;
import model.notification.Notification;

public class AdvisorMenuView {
	private Display display;
	private LocalResourceManager localResourceManager;
	private Shell shell;
	private List projectsList;

	public AdvisorMenuView(Display display) {
		this.display = display;
	}

	public static void main(String[] args) throws Exception {
		Advisor advisor = new Advisor();
		advisor.setEmail("alexandra@email.com");
		advisor.setPassword("alexandra");
		advisor.login();
		Display display = new Display();
		AdvisorMenuView window = new AdvisorMenuView(display);
		window.open(advisor);
	}

	public void open(Advisor advisor) {
		shell = new Shell(display);
		createResourceManager();
		shell.setText("Menu Principal");
		shell.setLayout(new GridLayout());

		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		CTabItem HomeTab = new CTabItem(tabFolder, SWT.NONE);
		HomeTab.setText("Inicio");

		Composite HomeComposite = new Composite(tabFolder, SWT.NONE);
		HomeComposite.setLayout(new GridLayout(2, false));
		HomeTab.setControl(HomeComposite);

		Composite NotificationsComposite = new Composite(HomeComposite, SWT.NONE);
		NotificationsComposite.setLayout(new GridLayout());
		NotificationsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		List notificationsList = new List(NotificationsComposite, SWT.BORDER | SWT.V_SCROLL);
		notificationsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite advisorView = new Composite(HomeComposite, SWT.NONE);
		advisorView.setLayout(new GridLayout());
		advisorView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label advisorLabel = new Label(advisorView, SWT.CENTER);
		advisorLabel.setText("Seja bem vindo(a), " + advisor.getName());
		advisorLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label advisorIDLabel = new Label(advisorView, SWT.CENTER);
		advisorIDLabel.setText("ID: " + advisor.getUserID());
		advisorIDLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label advisorEmailLabel = new Label(advisorView, SWT.CENTER);
		advisorEmailLabel.setText("Email: " + advisor.getEmail());
		advisorEmailLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		try {
			advisor.loadNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Notification notification : advisor.getNotifications()) {
			notificationsList.add(notification.getMessage());
		}

		CTabItem projectsTab = new CTabItem(tabFolder, SWT.NONE);
		projectsTab.setText("Projetos");

		Composite projectsComposite = new Composite(tabFolder, SWT.NONE);
		projectsComposite.setLayout(new GridLayout());

		Button newProjectsButton = new Button(projectsComposite, SWT.PUSH);
		newProjectsButton.setText("Novo Projeto");
		newProjectsButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

		newProjectsButton.addListener(SWT.Selection, e -> newProjectWindow(display, advisor));

		Composite projectDetailsComposite = new Composite(projectsComposite, SWT.NONE);
		projectDetailsComposite.setLayout(new GridLayout(2, false));
		projectDetailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		projectsList = new List(projectDetailsComposite, SWT.BORDER | SWT.V_SCROLL);
		projectsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		updateProjectsList(advisor);

		Composite projetoView = new Composite(projectDetailsComposite, SWT.NONE);
		GridLayout gl_projetoView = new GridLayout();
		gl_projetoView.verticalSpacing = 20;
		projetoView.setLayout(gl_projetoView);
		projetoView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		projectsList.addListener(SWT.Selection, e -> {
			String selectedProject = projectsList.getSelection()[0];
			updateProjetoView(projetoView, advisor.getProjectTCC(selectedProject));
		});

		projectsTab.setControl(projectsComposite);

		CTabItem chatsTab = new CTabItem(tabFolder, SWT.NONE);
		chatsTab.setText("Chats");

		Composite chatsComposite = new Composite(tabFolder, SWT.NONE);
		chatsComposite.setLayout(new GridLayout());
		chatsTab.setControl(chatsComposite);

		Label lblEmDesenvolvimento = new Label(chatsComposite, SWT.CENTER);
		lblEmDesenvolvimento.setText("Em desenvolvimento");
		lblEmDesenvolvimento.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		tabFolder.setSelection(0);

		shell.setSize(600, 400);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(), shell);
	}

	private void updateProjetoView(Composite projetoView, InterfaceProject project) {
		for (Control control : projetoView.getChildren()) {
			control.dispose();
		}

		Label projectLabel = new Label(projetoView, SWT.NONE);
		projectLabel.setText("Detalhes do Projeto: " + project.getTitle());
		projectLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, false, false));

		Label projectStatusLabel = new Label(projetoView, SWT.WRAP);
		projectStatusLabel.setText("Status: " + project.getStatus());
		projectStatusLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label projectGradeLabel = new Label(projetoView, SWT.WRAP);
		projectGradeLabel.setText("Nota Computada: " + String.valueOf(project.getGrade()));
		projectGradeLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label projectStudentLabel = new Label(projetoView, SWT.WRAP);
		projectStudentLabel.setText("Orientando: " + project.getStudent().getName());
		projectStudentLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Button tarefas = new Button(projetoView, SWT.PUSH);
		tarefas.setText("Visualizar tarefas");
		tarefas.addListener(SWT.Selection, e -> createTaskWindow(project, display));

		projetoView.layout();
	}

	private void updateProjectsList(Advisor advisor) {
		try {
			advisor.loadProjectList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		projectsList.removeAll();
		for (InterfaceProject projetos : advisor.getAllAssociatedProjects()) {
			projectsList.add(projetos.getTitle());
		}
	}

	public void newProjectWindow(Display display, Advisor advisor) {
		Shell projectsShell = new Shell(display);
		projectsShell.setText("Novo Projeto");
		projectsShell.setLayout(new GridLayout(2, false));
		projectsShell.setSize(300, 200);

		Label projectName = new Label(projectsShell, SWT.NONE);
		projectName.setText("Nome do Projeto:");
		Text projectNameText = new Text(projectsShell, SWT.BORDER);
		projectNameText.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label studentName = new Label(projectsShell, SWT.NONE);
		studentName.setText("ID do Aluno:");

		Text studentIdText = new Text(projectsShell, SWT.BORDER);
		studentIdText.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Button createProject = new Button(projectsShell, SWT.PUSH);
		createProject.setText("Criar Projeto");

		createProject.addListener(SWT.Selection, e -> {
			try {
				Student student = new Student();
				student.getStudentById(Integer.valueOf(studentIdText.getText()));
				advisor.createProject(projectNameText.getText(), student);
				projectsShell.close();
				updateProjectsList(advisor);
			} catch (Exception createError) {
				System.out.println(createError.getMessage());
			}

		});

		projectsShell.open();

		while (!projectsShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void createTaskWindow(InterfaceProject projeto, Display display) {
		Shell taskShell = new Shell(display);
		taskShell.setText("Lista de Tarefas");
		taskShell.setSize(600, 400);
		taskShell.setLayout(new GridLayout(1, false));

		Tree tree = new Tree(taskShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		GridData treeGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tree.setLayoutData(treeGridData);

		String[] headers = { "Nome", "Duração", "Data Início", "Entrega", "Status", "Ações" };
		for (String header : headers) {
			var column = new TreeColumn(tree, SWT.NONE);
			column.setText(header);
			column.setWidth(100);
		}

		populateTree(tree, projeto);

		Button addButton = new Button(taskShell, SWT.PUSH);
		addButton.setText("Adicionar Nova Tarefa");
		GridData buttonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		addButton.setLayoutData(buttonGridData);

		// Listener para o botão
		addButton.addListener(SWT.Selection, e -> {
			TaskCreate(projeto, display, tree);
		});

		taskShell.open();

		while (!taskShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void populateTree(Tree tree, InterfaceProject projeto) {
		for (TreeItem item : tree.getItems()) {
			item.dispose();
		}
		try {
			projeto.loadTaskList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Task task : projeto.getTasks()) {
			try {
				task.loadSubTaskList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			TreeItem taskItem = new TreeItem(tree, SWT.NONE);
			taskItem.setText(new String[] { task.getTitle(), task.getDuration().toString(),
					task.getStartDate().toString(), task.getEndDate().toString(), task.getStatus().toString() });

			TreeButtons(projeto, task, tree, taskItem);

			for (Task subtask : task.getSubTasks()) {

				TreeItem subtaskItem = new TreeItem(taskItem, SWT.NONE);

				subtaskItem.setText(new String[] { subtask.getTitle(), subtask.getDuration().toString(),
						subtask.getStartDate().toString(), subtask.getEndDate().toString(),
						subtask.getStatus().toString() });
				TreeButtons(projeto, subtask, tree, subtaskItem);
			}
		}
	}

	private void TaskEdit(InterfaceProject project, Task task, Display display, Tree tree) {
		Shell newTaskShell = new Shell(display);
		newTaskShell.setText("Editar Tarefa");
		newTaskShell.setSize(300, 200);
		newTaskShell.setLayout(new GridLayout(1, false));

		Label taskNameLabel = new Label(newTaskShell, SWT.CENTER);
		taskNameLabel.setText("Nome da tarefa: " + task.getTitle());
		taskNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label taskDescriptionLabel = new Label(newTaskShell, SWT.CENTER);
		taskDescriptionLabel.setText("Descrição da tarefa: " + task.getDescription());
		taskDescriptionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		// É uma subtarefa?
		if (!task.isSubTask()) {
			if (task.getStatus() != "COMPLETA") {
				Label taskDurationLabel = new Label(newTaskShell, SWT.CENTER);
				taskDurationLabel.setText("A tarefa está aberta a: " + task.getDuration().toString() + " dias");
				taskDurationLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

				Button subTaskButton = new Button(newTaskShell, SWT.PUSH);
				subTaskButton.setText("Adicionar Sub Tarefa");
				GridData subTaskGridData = new GridData(SWT.LEFT, SWT.LEFT, false, false);
				subTaskGridData.horizontalSpan = 2;
				subTaskButton.setLayoutData(subTaskGridData);

				subTaskButton.addListener(SWT.Selection, event -> {
					subTaskCreate(project, task, display, tree);
					newTaskShell.close();
				});
			}

			if (!task.haveSubtasks()) {
				if (task.getStatus().equalsIgnoreCase("COMPLETA")) {
					Button DocumentVisualizeButton = new Button(newTaskShell, SWT.PUSH);
					DocumentVisualizeButton.setText("Visualizar Entrega");
					GridData FeedbackGridData = new GridData(SWT.LEFT, SWT.LEFT, false, false);
					FeedbackGridData.horizontalSpan = 2;
					DocumentVisualizeButton.setLayoutData(FeedbackGridData);

					DocumentVisualizeButton.addListener(SWT.Selection, event -> {
						feedbackShell(newTaskShell, task, project, tree);
						newTaskShell.close();
					});
				}
			}
		} else {
			if (task.getStatus().equalsIgnoreCase("COMPLETA")) {
				Button DocumentVisualizeButton = new Button(newTaskShell, SWT.PUSH);
				DocumentVisualizeButton.setText("Visualizar Entrega");
				GridData FeedbackGridData = new GridData(SWT.LEFT, SWT.LEFT, false, false);
				FeedbackGridData.horizontalSpan = 2;
				DocumentVisualizeButton.setLayoutData(FeedbackGridData);

				DocumentVisualizeButton.addListener(SWT.Selection, event -> {

					feedbackShell(newTaskShell, task, project, tree);
					newTaskShell.close();
				});
			}
		}
		newTaskShell.open();
	}

	private void TaskCreate(InterfaceProject projeto, Display display, Tree tree) {
		Shell newTaskShell = new Shell(display);
		newTaskShell.setText("Nova Tarefa");
		newTaskShell.setSize(300, 200);
		newTaskShell.setLayout(new GridLayout(2, false));

		// Campos de entrada para a nova tarefa
		new Label(newTaskShell, SWT.NONE).setText("Nome:");
		Text nameText = new Text(newTaskShell, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(newTaskShell, SWT.NONE).setText("Descrição:");
		Text descriptionText = new Text(newTaskShell, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(newTaskShell, SWT.NONE).setText("Data Início:");
		DateTime startDate = new DateTime(newTaskShell, SWT.DATE | SWT.DROP_DOWN);
		startDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(newTaskShell, SWT.NONE).setText("Entrega:");
		DateTime endDate = new DateTime(newTaskShell, SWT.DATE | SWT.DROP_DOWN);
		endDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button saveButton = new Button(newTaskShell, SWT.PUSH);
		saveButton.setText("Criar Tarefa");
		GridData saveButtonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		saveButtonGridData.horizontalSpan = 2;
		saveButton.setLayoutData(saveButtonGridData);

		saveButton.setLayoutData(saveButtonGridData);

		saveButton.addListener(SWT.Selection, event -> {
			Calendar cal_end = Calendar.getInstance();
			cal_end.set(endDate.getYear(), endDate.getMonth(), endDate.getDay(), 0, 0, 0);
			cal_end.set(Calendar.MILLISECOND, 0);
			Date endDateObj = new Date(cal_end.getTimeInMillis());

			Calendar cal_start = Calendar.getInstance();
			cal_start.set(startDate.getYear(), startDate.getMonth(), startDate.getDay(), 0, 0, 0);
			cal_start.set(Calendar.MILLISECOND, 0);
			Date startDateObj = new Date(cal_start.getTimeInMillis());

			try {
				projeto.createTask(startDateObj, endDateObj, nameText.getText(), descriptionText.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			populateTree(tree, projeto);
			newTaskShell.close();
		});

		newTaskShell.open();

	}

	private void subTaskCreate(InterfaceProject project, Task task, Display display, Tree tree) {
		Shell newTaskShell = new Shell(display);
		newTaskShell.setText("Nova Tarefa");
		newTaskShell.setSize(300, 200);
		newTaskShell.setLayout(new GridLayout(2, false));

		// Campos de entrada para a nova tarefa
		new Label(newTaskShell, SWT.NONE).setText("Nome:");
		Text nameText = new Text(newTaskShell, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(newTaskShell, SWT.NONE).setText("Descrição:");
		Text descriptionText = new Text(newTaskShell, SWT.BORDER);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(newTaskShell, SWT.NONE).setText("Data Início:");
		DateTime startDate = new DateTime(newTaskShell, SWT.DATE | SWT.DROP_DOWN);
		startDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(newTaskShell, SWT.NONE).setText("Entrega:");
		DateTime endDate = new DateTime(newTaskShell, SWT.DATE | SWT.DROP_DOWN);
		endDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button saveButton = new Button(newTaskShell, SWT.PUSH);
		saveButton.setText("Criar Tarefa");
		GridData saveButtonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		saveButtonGridData.horizontalSpan = 2;
		saveButton.setLayoutData(saveButtonGridData);

		saveButton.setLayoutData(saveButtonGridData);

		saveButton.addListener(SWT.Selection, event -> {
			Calendar cal_end = Calendar.getInstance();
			cal_end.set(endDate.getYear(), endDate.getMonth(), endDate.getDay(), 0, 0, 0);
			cal_end.set(Calendar.MILLISECOND, 0);
			Date endDateObj = new Date(cal_end.getTimeInMillis());

			Calendar cal_start = Calendar.getInstance();
			cal_start.set(startDate.getYear(), startDate.getMonth(), startDate.getDay(), 0, 0, 0);
			cal_start.set(Calendar.MILLISECOND, 0);
			Date startDateObj = new Date(cal_start.getTimeInMillis());

			try {
				task.createSubTask(startDateObj, endDateObj, nameText.getText(), descriptionText.getText());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			populateTree(tree, project);
			newTaskShell.close();
		});

		newTaskShell.open();
	}

	public void TreeButtons(InterfaceProject project, Task task, Tree tree, TreeItem taskItem) {
		// Cria um Composite para conter os botões
		Composite composite = new Composite(tree, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		// Cria o botão Editar
		Button editButton = new Button(composite, SWT.PUSH);
		editButton.setText("Expandir");
		editButton.addListener(SWT.Selection, e -> {
			TaskEdit(project, task, Display.getDefault(), tree);
		});

		// Cria um editor para a célula que terá os botões
		TreeEditor editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.CENTER;
		editor.grabHorizontal = true;
		editor.minimumWidth = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		editor.setEditor(composite, taskItem, 5); // Índice 5 para a coluna "Ações"
	}

	public void feedbackShell(Shell feedbackShell, Task task, InterfaceProject project, Tree tree) {

		Shell documentShell = new Shell(feedbackShell.getDisplay());
		documentShell.setText("Detalhes do Documento");
		documentShell.setLayout(new GridLayout(2, false)); // GridLayout com 2 colunas

		// Nome do Documento
		Label documentNameLabel = new Label(documentShell, SWT.NONE);
		documentNameLabel.setText("Título do Documento: " + task.getDocument().getTitle());
		documentNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		// Botão para Download
		Button downloadButton = new Button(documentShell, SWT.PUSH);
		downloadButton.setText("Download do Documento");
		GridData downloadButtonData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		downloadButtonData.horizontalAlignment = SWT.FILL;
		downloadButton.setLayoutData(downloadButtonData);
		downloadButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				FileDialog fileDialog = new FileDialog(documentShell, SWT.SAVE);

		
				String fileExtension = task.getDocument().getFileExtension();
				String[] filterExtensions = { "*." + fileExtension, "*.*" };
				
				fileDialog.setFilterExtensions(filterExtensions);
				String selectedPath = fileDialog.open();
				fileDialog.setFileName(selectedPath);
				
				if (selectedPath != null) {
						try {
							task.getDocument().copyFile(selectedPath);
							
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
					}
			}

		});

		if (task.getFeedback() == null) {
			Text feedbackText = new Text(documentShell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			GridData feedbackTextData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			feedbackTextData.heightHint = 100;
			feedbackText.setLayoutData(feedbackTextData);

			Label numberTextLabel = new Label(documentShell, SWT.NONE);
			numberTextLabel.setText("Nota da tarefa");
			numberTextLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

			Text numberText = new Text(documentShell, SWT.BORDER);
			GridData numberTextData = new GridData(SWT.FILL, SWT.CENTER, true, false);
			numberText.setLayoutData(numberTextData);

			Button sendResponseButton = new Button(documentShell, SWT.PUSH);
			sendResponseButton.setText("Enviar Resposta");
			GridData sendResponseButtonData = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
			sendResponseButtonData.horizontalAlignment = SWT.FILL;
			sendResponseButton.setLayoutData(sendResponseButtonData);

			sendResponseButton.addListener(SWT.Selection, sendResponseEvent -> {
				if (Double.valueOf(numberText.getText()) < 10 || Double.valueOf(numberText.getText()) > 0) {
					task.CreateFeedback(feedbackText.getText());
					try {
						task.addTaskGrade(Double.valueOf(numberText.getText()));
					} catch (NumberFormatException | SQLException e1) {
						e1.printStackTrace();
					}
					for (Control control : tree.getChildren()) {
						control.dispose();
					}
					populateTree(tree, project);
					documentShell.close();
				}
			});
		} else {
			Label gradeTextLabel = new Label(documentShell, SWT.NONE);
			gradeTextLabel.setText("Nota da tarefa: " + task.getGrade().toString());
			gradeTextLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

			Text feedbackText = new Text(documentShell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			feedbackText.setText(task.getFeedback().getBody());
			feedbackText.setEditable(false);
			GridData feedbackTextData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			feedbackTextData.heightHint = 100;
			feedbackText.setLayoutData(feedbackTextData);

		}

		// Ajustar o tamanho da janela e abrir
		documentShell.setSize(400, 300);
		documentShell.setLocation(200, 200); // Definir posição da janela
		documentShell.open();
	}

}
