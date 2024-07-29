class computer {

    private String cpu;
    private String ram;
    private String storage;

    public void setCPU(String cpu) {
        this.cpu = cpu;
    }
    
    public void setRAM(String ram) {
        this.ram = ram;
    }

    public void setstorage(String storage) {
        this.storage = storage;
    }

    public void displayInfo() {
        System.out.println("Computer Configuration:");
        System.out.println("CPU: " + cpu);
        System.out.println("RAM: " + ram);
        System.out.println("Storage: " + storage);
        System.out.println();
    }
}

interface CPUBuilder  {
    void buildCPU();
    void buildRAM();
}