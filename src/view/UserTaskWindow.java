package view;

import java.time.LocalDate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import model.Advisor;
import model.Document;
import model.ProjectTCC;
import model.Student;
import model.Task;

public class UserTaskWindow {

	public ProjectTCC createData() {
		Student student = new Student("pedro","Pclemonini",123,"Familia");
		Advisor advisor = new Advisor("Joao","Jclemonini",124,"Familia");
		ProjectTCC projeto = new ProjectTCC("Projeto", advisor, student);
		Document documento = new Document("Titulo", "Documento 1", "docu.pdf");
		
		projeto.createTask(LocalDate.now(), LocalDate.now().plusDays(10), "tarefa1", "tarefa-faca");
		projeto.createTask(LocalDate.now(), LocalDate.now().plusDays(10), "tarefa2", "tarefa-faca");
		projeto.createTask(LocalDate.now(), LocalDate.now().plusDays(10), "tarefa3", "tarefa-faca");
		
		projeto.createSubTask(projeto.getTaskByName("tarefa1"), LocalDate.now(), LocalDate.now().plusDays(10), "sub-tarefa", "sub-tarefa-faca");
		projeto.createSubTask(projeto.getTaskByName("tarefa1"), LocalDate.now(), LocalDate.now().plusDays(10), "sub-tarefa2", "sub-tarefa-faca2");
		
		try {
		projeto.attachDocument("tarefa2", documento);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
			
		return projeto;
	}

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UserTaskWindow window = new UserTaskWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("Lista de Tarefas");
		shell.setSize(600, 400);
		shell.setLayout(new FillLayout());


		ProjectTCC projeto = createData();
		
		Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		
		
		projeto.getAdvisor().getNotifications();
		
		String[] headers = {"Nome", "Duração", "Data Início", "Entrega", "Status"};
		for (String header : headers) {
			var column = new TreeColumn(tree, SWT.NONE);
			column.setText(header);
			column.setWidth(100); 	
		}
			
		for (Task task : projeto.getTaskList()) {
			TreeItem taskItem = new TreeItem(tree, SWT.NONE);
			taskItem.setText(new String[]{
					task.getTitle(),
					task.getDuration().toString(),
					task.getStartDate().toString(),
					task.getEndDate().toString(),
					task.getStatus().toString()
			});
			taskItem.setData("task", task);

			if (task.haveSubtasks()) {
				for (Task subtask : task.getSubTasks()) {
					TreeItem subtaskItem = new TreeItem(taskItem, SWT.NONE);
					subtaskItem.setText(new String[]{
							subtask.getTitle(),
							subtask.getDuration().toString(),
							subtask.getStartDate().toString(),
							subtask.getEndDate().toString(),
							subtask.getStatus().toString()
					});
					subtaskItem.setData("task", subtask);
				}
				taskItem.setExpanded(false);
			}


		}
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				TreeItem selectedItem = (TreeItem) event.item;

				Task selectedTask = (Task) selectedItem.getData("task");
				if(selectedTask.haveSubtasks()) {

				}else {
					openTaskDetailsWindow(Display.getDefault(), selectedTask);
				}

			}
		});

	}

	private static void openTaskDetailsWindow(Display display, Task task) {
		Shell detailsShell = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		detailsShell.setText("Detalhes da Tarefa");
		detailsShell.setSize(400, 300);
		detailsShell.setLayout(new FillLayout());

		Label label = new Label(detailsShell, SWT.NONE);
		label.setText("Detalhes da Tarefa: " + task.getTitle());

		detailsShell.open();
		detailsShell.layout();
		
		while (!detailsShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
	}


}
