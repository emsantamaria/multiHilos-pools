package com.docencia.hilos;

public class ServidorMazmorras {
    static class PeticionMazmorra implements Runnable{
        private final String nombreJugador;
        private final String mazmorra;
        public PeticionMazmorra(String nombreJugador, String mazmorra) {
            this.nombreJugador = nombreJugador;
            this.mazmorra = mazmorra;
        }
        @Override
        public void run() {
            String hilo=Thread.currentThread().getName();
            System.out.println("El jugador "+nombreJugador+" esta entrando a la mazmorra "+mazmorra+" en el hilo "+hilo);
            try{
                Thread.sleep(1000+(int)(Math.random()*1000));
            }catch(Exception e){
                System.out.println("Error en el hilo "+hilo);
                Thread.currentThread().interrupt();
                return;
            }
            System.out.println("El jugador "+nombreJugador+" ha completado la mazmorra "+mazmorra+" en el hilo "+hilo);
        }
    }
    public static void main(String[] args) {
        ExexcutorService gmBots=Executors.newFixedThreadPool(3);
        String[] jugadores={"Ana","Luis","Carlos","Marta","Sofia","Diego","Elena","Jorge","juan","goku"};
        String[] mazmorras={"Cueva del dragon","Bosque encantado","Castillo oscuro","Templo perdido"};
        for(int i=0;i<jugadores.length;i++){
            String jugador=jugadores[i];
            String mazmorra=mazmorras[i%mazmorras.length];
            PeticionMazmorra peticion=new PeticionMazmorra(jugador,mazmorra);
            gmBots.execute(peticion);
        }
        gmBots.shutdown();
        System.out.println("Todas las peticiones de mazmorras han sido enviadas.");
    }
        
  
}
