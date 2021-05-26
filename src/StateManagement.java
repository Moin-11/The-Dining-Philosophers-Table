/* A Controller that determines the state of the application, implemented handler methods that manipulate the state of the application and determine it. */

// This code has six main classes:
//  Dining
//      The public, "main" class.  Set up so the code can run either
//      stand-alone or as an applet in a web page or in appletviewer.
//  Philosopher
//      Active -- extends Thread
//  Fork
//      Passive
//  Table
//      Manages the philosophers and forks and their physical layout.
//  Coordinator
//      Provides mechanisms to suspend, resume, and reset the state of
//      worker threads (philosophers).
//  UI
//      Manages graphical layout and button presses.

// The Coordinator serves to slow down execution, so that behavior is
// visible on the screen, and to notify all RUN threads when the user
// wants them to reset.
//
class StateManagement {
    public enum State {PAUSED, RUN, RESET}


    private State state = State.PAUSED;

    public synchronized boolean isPaused() {
        return (state == State.PAUSED);
    }

    public synchronized void pause() {
        state = State.PAUSED;
    }

    public synchronized boolean isReset() {
        return (state == State.RESET);
    }

    public synchronized void reset() {
        state = State.RESET;
    }

    public synchronized void resume() {
        state = State.RUN;
        notifyAll();        
    }

    public synchronized boolean gate() throws ResetException {



        if (state == State.PAUSED ^ state == State.RESET) {
            try {
                wait();
            } catch(InterruptedException e) {
                if (isReset()) {
                    throw new ResetException();
                }
            }
            return true;        
        }
        return false;           
    }
}
