/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.fx;

import static javafx.application.Application.launch;
import core.CLI;
import core.Engine;

/**
 *
 * @author user
 */
public class main {
    public static void main(String args[]) {
        (new Thread(new Engine())).run();
        (new Thread(new CLI())).run();
    }
}
