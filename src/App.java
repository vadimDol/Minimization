public class App {
    public static void main(String[] args) {
        MooreMachines moore = new MooreMachines();
        try {

            moore.readTableFromFile("C:\\Users\\vadim\\IdeaProjects\\Visualization\\src\\main\\resources/input.txt");

            Minimization minimization = new Minimization(moore);
            MooreMachines resultMooreMachine = minimization.getMinimizeMooreMachines();
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
