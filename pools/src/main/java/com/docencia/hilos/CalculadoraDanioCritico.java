package com.docencia.hilos;
import java.util.Objects;

public class CalculadoraDanioCritico{
    static class Ataque{

        final String atacante;
        final int danioBase;
        final double probCritico;
        final double multiplicadorCritico;

        public Ataque(String atacante, int danioBase, double probCritico, double multiplicadorCritico) {
            this.atacante = atacante;
            this.danioBase = danioBase;
            this.probCritico = probCritico;
            this.multiplicadorCritico = multiplicadorCritico;
        }
        int getDanioBase(){
            return danioBase;
        }
        String getAtacante() {
            return atacante;
        }
        double getProbCriticco(){
            return probCritico;
        }
        double getMultiplicador(){
            return multiplicadorCritico;
        }
    }
    static class TareaCalcularDanio implements Callable<Integer>{
        private final Ataque ataque;
        public TareaCalcularDanio(Ataque ataque) {
            this.ataque = ataque;
        }
        @Override
        public Integer call()throws Exception{
            String hilo=Thread.currentThread().getName();
            System.out.println("Calculando danio para el atacante "+ataque.getAtacante()+" en el hilo "+hilo);
           boolean esCritico=Math.random()<ataque.getProbCriticco();
           double multiplicador=esCritico?ataque.getMultiplicador():1.0;
           Thread.sleep(500+(int)(Math.random()*500));

           int danioFinal=(int)(ataque.getDanioBase()*multiplicador);
            System.out.println("El atacante "+ataque.getAtacante()+" ha infligido un danio de "+danioFinal+" (critico: "+esCritico+") en el hilo "+hilo);
            return danioFinal;
        }   
    }
    public Static void main(String[] args) {
        ExecutorService pool=Executors.newFixedThreadPool(4);
        List<Futere<Integer>> resultados=new ArrayList<>();
        Ataque[] ataques={
            new Ataque("Guerrero",100,0.2,2.0),
            new Ataque("Mago",80,0.3,2.5),
            new Ataque("Arquero",90,0.25,2.2),
            new Ataque("Asesino",70,0.4,3.0),
            new Ataque("Paladin",110,0.15,1.8),
            new Ataque("Berserker",120,0.1,2.0),
            new Ataque("Cazador",85,0.35,2.3),
            new Ataque("Hechicero",95,0.28,2.4)
        };
        for(Ataque ataque:ataques){
            TareaCalcularDanio tarea=new TareaCalcularDanio(ataque);
            Future<Integer> resultado=pool.submit(tarea);
            resultados.add(resultado);
        }
        int totalRaid=0;
        for(int i=0;i<ataques.size();i++){
            try{
                int danio=resultados.get(i).get();
                totalRaid+=danio;
            }catch(Exception e){
                Thread.currentThread().interrupt();
                System.out.println("Error al obtener el resultado del ataque "+(i+1));
            }
        }
    }
}