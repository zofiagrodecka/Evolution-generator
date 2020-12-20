package project;

public class SimulationEngine{

    private Simulation simulation;

    public SimulationEngine(Simulation mapSimulation) {
        this.simulation = mapSimulation;
    }

    public void runSimulation() {
        System.out.println("I'm inside run");
        try {
            simulation.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
