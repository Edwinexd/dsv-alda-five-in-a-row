/* 
dsv-alda-five-in-a-row: A Java implementation of the game "Five in a row" (Gomoku) with an AI using the Minimax algorithm and alpha-beta pruning.
Copyright (C) 2024 Edwin Sundberg

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
