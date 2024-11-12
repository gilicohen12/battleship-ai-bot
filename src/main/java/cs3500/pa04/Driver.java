package cs3500.pa04;

import cs3500.pa03.controller.SalvoController;
import cs3500.pa03.view.SalvoView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    Random random = new Random();

    ProxyController proxyController = new ProxyController(server, random);
    proxyController.run();
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      SalvoView view = new SalvoView(input, output);
      SalvoController salvo = new SalvoController(view);
      salvo.runGame(false);
    } else if (args.length == 2) {
      try {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        runClient(host, port);
      } catch (IOException | IllegalStateException e) {
        System.out.println("Unable to connect to the server.");
      } catch (NumberFormatException e) {
        System.out.println("Second argument should be an integer. Format: `[host] [part]`.");
      }
    } else {
      throw new IllegalArgumentException("These arguments are illegal, if you would"
          + " like to use the PA03 implementation pass in no args, if you would like to use "
          + "the PA04 implementation pass in a host and a port");
    }


  }
}