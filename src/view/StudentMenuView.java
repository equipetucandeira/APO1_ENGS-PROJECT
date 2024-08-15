package view;

import java.sql.Date;
import java.sql.SQLException;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
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

import model.*;
import model.notification.FeedbackNotification;
import model.notification.Notification;
import model.notification.TaskNotification;

public class StudentMenuView {
	private Display display;
	private LocalResourceManager localResourceManager;
	private Shell shell;
	private List projectsList;

	public StudentMenuView(Display display) {
		this.display = display;
	}

	public static void main(String[] args) throws Exception {
		Student student = new Student();
		student.setEmail("pedro@email.com");
		student.setPassword("pedro");
		student.login();

		Display display = new Display();
		StudentMenuView window = new StudentMenuView(display);
		window.open(student);
	}

	public void open(Student student) {
		try {
			student.loadProject();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shell = new Shell(display);
		createResourceManager();
		shell.setText("Menu Principal");
		shell.setLayout(new GridLayout());

		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		CTabItem HomeTab = new CTabItem(tabFolder, SWT.NONE);
		HomeTab.setText("Inicio");

		Composite HomeComposite = new Composite(tabFolder, SWT.NONE);
		HomeComposite.setLayout(new GridLayout(2,false));
		HomeTab.setControl(HomeComposite);

		Composite NotificationsComposite = new Composite(HomeComposite,SWT.NONE);
		NotificationsComposite.setLayout(new GridLayout());
		NotificationsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		List notificationsList = new List(NotificationsComposite, SWT.BORDER | SWT.V_SCROLL);
		notificationsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite studentView = new Composite(HomeComposite, SWT.NONE);
		studentView.setLayout(new GridLayout());
		studentView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label studentLabel = new Label(studentView, SWT.CENTER);
		studentLabel.setText("Seja bem vindo(a), " + student.getName());
		studentLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label studentIDLabel = new Label(studentView, SWT.CENTER);
		studentIDLabel.setText("ID: " + student.getUserID());
		studentIDLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label studentEmailLabel = new Label(studentView, SWT.CENTER);
		studentEmailLabel.setText("Email: " + student.getEmail());
		studentEmailLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
		
		try {
			student.loadNotification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Notification notification: student.getNotifications()) {
			notificationsList.add(notification.getMessage());
		}

		CTabItem projectsTab = new CTabItem(tabFolder, SWT.NONE);
		projectsTab.setText("Seu Projeto");

		Composite projectsComposite = new Composite(tabFolder, SWT.NONE);
		projectsComposite.setLayout(new GridLayout());
		
		if(student.getProject() != null) {
		Label projectName = new Label(projectsComposite, SWT.TOP);
		projectName.setText("Nome do Projeto: "+ student.getProject().getTitle());

		Label advisorName = new Label(projectsComposite, SWT.TOP);
		advisorName.setText("Nome do Orientador: "+ student.getProject().getAdvisor().getName());

		Label projectStatus = new Label(projectsComposite, SWT.TOP);
		projectStatus.setText("Status do projeto: "+ student.getProject().getStatus());

		Label projectGrade = new Label(projectsComposite, SWT.TOP);
		projectGrade.setText("Nota atual: ");

		Composite projectDetailsComposite = new Composite(projectsComposite, SWT.BOTTOM);
		projectDetailsComposite.setLayout(new GridLayout(2, false));
		projectDetailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
			createTaskWindow(student.getProject(),projectDetailsComposite);
		
		}else {
			Label projectName = new Label(projectsComposite, SWT.TOP);
			projectName.setText("Você não foi cadastrado em nenhum projeto");
		}
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
	public void createTaskWindow(InterfaceProject projeto, Composite taskComposite) {

		Tree tree = new Tree(taskComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		GridData treeGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tree.setLayoutData(treeGridData);

		String[] headers = { "Nome", "Duração", "Data Início", "Entrega", "Status","Ações"};
		for (String header : headers) {
			var column = new TreeColumn(tree, SWT.NONE);
			column.setText(header);
			column.setWidth(100);
		}

		populateTree(tree, projeto);

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

			TreeButtons(projeto,task,tree,taskItem);       

			for (Task subtask : task.getSubTasks()) {

				TreeItem subtaskItem = new TreeItem(taskItem, SWT.NONE);

				subtaskItem.setText(new String[] { subtask.getTitle(), subtask.getDuration().toString(),
				subtask.getStartDate().toString(), subtask.getEndDate().toString(), subtask.getStatus().toString() });
				TreeButtons(projeto,subtask,tree,subtaskItem);   
			}
		}
	}

	public void TreeButtons(InterfaceProject project,Task task,Tree tree,TreeItem taskItem) {
		Composite composite = new Composite(tree, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Button editButton = new Button(composite, SWT.PUSH);
		editButton.setText("Visualizar");
		editButton.addListener(SWT.Selection, e -> {
			TaskVisualize(project,task, Display.getDefault(), tree);
		});

		// Cria um editor para a célula que terá os botões
		TreeEditor editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.CENTER;
		editor.grabHorizontal = true;
		editor.minimumWidth = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		editor.setEditor(composite, taskItem, 5); // Índice 5 para a coluna "Ações"
	}

	public void TaskVisualize(InterfaceProject project,Task task, Display display, Tree tree) {
		Shell newTaskShell = new Shell(display);
		newTaskShell.setText("Informações da Tareda");
		newTaskShell.setSize(300, 200);
		newTaskShell.setLayout(new GridLayout(1, false));

		Label taskNameLabel = new Label(newTaskShell, SWT.CENTER);
		taskNameLabel.setText("Nome da tarefa: " + task.getTitle());
		taskNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));

		Label taskDescriptionLabel = new Label(newTaskShell, SWT.CENTER);
		taskDescriptionLabel.setText("Descrição da tarefa: " + task.getDescription());
		taskDescriptionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
		if(task.getStatus().equalsIgnoreCase("INCOMPLETA")) {
				Label taskDurationLabel = new Label(newTaskShell, SWT.CENTER);
				taskDurationLabel.setText("A tarefa está aberta a: " + task.getDuration().toString() + " dias");
				taskDurationLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
		}else {
			Label taskCompleteLabel = new Label(newTaskShell, SWT.CENTER);
			taskCompleteLabel.setText("Entrega realizada, tarefa completa");
			taskCompleteLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
			Label taskGradeLabel = new Label(newTaskShell, SWT.CENTER);
			if(task.getFeedback() != null) {
			taskGradeLabel.setText("Nota: " + task.getGrade());
			taskGradeLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
			
			Label taskFeedbackLabel = new Label(newTaskShell, SWT.CENTER);
			taskFeedbackLabel.setText("Feedback: " + task.getFeedback().getBody());
			taskFeedbackLabel.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, false));
			}
		}

		
		if(!task.isSubTask()) {
			if(task.haveSubtasks()) {
			}else {
				addDocumentShell(newTaskShell,task,tree,project);
			}
		}else {
			addDocumentShell(newTaskShell,task,tree,project);	
		}


		newTaskShell.open();
	}


	public void addDocumentShell(Shell documentShell,Task task,Tree tree, InterfaceProject project) {
		if(task.getStatus().equalsIgnoreCase("INCOMPLETA")) {
		Button FeedbackButton = new Button(documentShell, SWT.PUSH);
		FeedbackButton.setText("Adicionar Entrega");
		GridData FeedbackGridData = new GridData(SWT.LEFT, SWT.LEFT, false, false);
		FeedbackGridData.horizontalSpan = 2;
		FeedbackButton.setLayoutData(FeedbackGridData);

		FeedbackButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// Criação da nova janela (Shell)
				Shell uploadShell = new Shell(documentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				uploadShell.setText("Anexar Documento");
				uploadShell.setLayout(new GridLayout(2, false));

				// Label e Text para exibir o caminho do arquivo selecionado
				Label fileNameLabel = new Label(uploadShell, SWT.NONE);
				fileNameLabel.setText("Titulo do Document:");
				
				Text fileNameText = new Text(uploadShell, SWT.BORDER);
				fileNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				
				Text filePathText = new Text(uploadShell, SWT.BORDER);
				filePathText.setVisible(false);
				
				// Botão para selecionar o arquivo
				Button selectFileButton = new Button(uploadShell, SWT.PUSH);
				selectFileButton.setText("Selecionar Arquivo");
				selectFileButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event e) {
						FileDialog fileDialog = new FileDialog(uploadShell, SWT.OPEN);
						fileDialog.setFilterExtensions(new String[]{"*.pdf", "*.docx", "*.txt"});
						String selectedFile = fileDialog.open();
						
							if (selectedFile != null) {
								filePathText.setText(selectedFile);
							}	
					}
				});

				// Botão para enviar o arquivo
				Button uploadButton = new Button(uploadShell, SWT.PUSH);
				uploadButton.setText("Enviar");
				uploadButton.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event e) {
						String filePath = filePathText.getText();
						String fileName = fileNameText.getText();
						if (!filePath.isEmpty() && !fileName.isEmpty()) {
							 
							try {
								task.attachDocument(filePath,fileName);
								Notification notification = new TaskNotification(project);
								notification.sendNotification();
								 for (Control control : tree.getChildren()) {
								        control.dispose();
								    }
								 populateTree(tree,project);
								 documentShell.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					
				});

				
				GridData buttonGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
				buttonGridData.horizontalSpan = 2;
				uploadButton.setLayoutData(buttonGridData);

				uploadShell.pack();
				uploadShell.open();
			}
		});
	}
		
	}
}
