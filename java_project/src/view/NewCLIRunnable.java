package view;

public class NewCLIRunnable implements Runnable {
	
	NewCLI cli;
	public NewCLIRunnable(NewCLI cli) {
		super();
		this.cli = cli;
	}
	@Override
	public void run() {
		cli.start();
	}

}
