public class BerlinUhr {
    public int hour;
    public int minute;
    public int second;


    public String getTopHours(int arg) {
        hour = arg;
        int result = hour/5;
        hour = hour - (result*5);

        if (result == 1) {
            return "R000";
        }
        else if (result == 2){
            return "RR00";
        }
        else if (result == 3) {
            return "RRR0";
        }
        else if (result == 4){
                return "RRRR";
        }
        return "0000";
    }
    //testTopMinutes()
    //Liste vide ,boucle qui avance en 5 dans les minutes ,liste+="J" , a la fin de la boucle il ajout un "R"
    //puis on complete par des zeros ou au lieu de liste vide on met une liste de zeros.
    //on eneleve les minutes a garder pour la methode de bottom minutes comme on a fait plus haut ;)
    }
