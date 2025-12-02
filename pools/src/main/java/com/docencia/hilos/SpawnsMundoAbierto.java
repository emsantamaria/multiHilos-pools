public class SpawnsMundoAbierto{
    static class SpawnArea implements Runnable{
        private final String[] zonas={"Bosque","Desierto","Montaña","Pantano","Llanura"};
        private final String[] enemigos={"Goblin","Orco","Troll","Esqueleto","Zombi"};
        @Override
        public void run(){
            String hilo = Thread.currentThread().getName();
            String zona = zonas[(int)(Math.random()*zonas.length)];
            String enemigo = enemigos[(int)(Math.random()*enemigos.length)];
            System.out.println("Spawneando enemigo "+enemigo+" en la zona "+zona+" en el hilo "+hilo);
        }
    }
    public static void main(String[] args){
    ScheduledExecutorService scheduler=Executors.newScheduleddThreadPool(2);
    scheduler.scheduleAtFixedRate(new SpawnArea(),0,2,TimeUnit.SECONDS);
    Thread.sleep(12000);
    System.out.println("Deteniendo spawns de enemigos.");
    scheduler.shutdown();
    if(!scheduler.awaitTermination(5,TimeUnit.SECONDS)){
        System.out.println("Forzando cierre del scheduler.");
        scheduler.shutdownNow();
    }
    System.out.println("Scheduler detenido.");
    }
}