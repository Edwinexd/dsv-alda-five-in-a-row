package com.edsv;

public class MaxIT {
    /* 
    // alpha = COMP_LOSS, beta = COMP_WIN
    // vad representerar konstanterna, hur fungerar utvärderingsfunktionen
    private MoveInfo findCompMove(int alpha, int beta) {
        // måste göras om
        if (fullBoard()) {
            value = DRAW; // då skickar man resultatet ur det fallet
        // om vi kan vinna direkt
        } else if (quickWinInfo = immediateComputerWin() != null) {
            return quickWinInfo;
        } else {
            // Den riktiga intressanta metoden
            value = alpha; // bästa jag har sett hittils
            // gå igenom alla rutorna så länge som vi inte vet att vi har hittat en som är garanterat sämre
            for (each possible position && value < beta) {
                if (possible to place(position)) {
                    place(position, COMP); // tveksamt om man bryr sig om vem
                    // mitt nuvarande bästa värde blir dess alpha nivå
                    // beta uppdateras bara av findHumanMove
                    // TODO: Vad är responseValue
                    // det uppenbara är poängen, viktiga är skillnaden i poäng mellan spelare och dator. funkar om vi kan gå hela vägen ner
                    // att ge den andra förre möjligheter är antagligen det bästa men svårt att utvärdera
                    responseValue = findHumanMove(value, beta).value; // "min", det är det här som är utvärderingen
                    unplace(position); // man vill ju kunna testa alla andra
                    if (responseValue > value) { // vi har hittat ett bättre
                        value = responseValue;
                        bestMove = position;
                    }
                }
            }
        }
        return new MoveInfo(bestMove, value);
    }
    */
}
