package akka.eventbus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;

/**
 * 入口
 */
public class Main {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("DeadLetters");
        final ActorRef actor = system.actorOf(Props.create(DeadLetterActor.class));
        system.eventStream().subscribe(actor, DeadLetter.class);

        final ActorRef jazzListener = system.actorOf(Props.create(Listener.class));
        final ActorRef musicListener = system.actorOf(Props.create(Listener.class));
        system.eventStream().subscribe(jazzListener, AllKindsOfMusic.Jazz.class);
        system.eventStream().subscribe(musicListener, AllKindsOfMusic.class);
        final ActorRef jazzListener2 = system.actorOf(Props.create(Listener2.class));
        final ActorRef musicListener2 = system.actorOf(Props.create(Listener2.class));
        system.eventStream().subscribe(jazzListener2, AllKindsOfMusic.Jazz.class);
        system.eventStream().subscribe(musicListener2, AllKindsOfMusic.class);


        system.eventStream().publish(new AllKindsOfMusic.Electronic("Parov Stelar"));
        system.eventStream().publish(new AllKindsOfMusic.Jazz("Sonny Rollins"));


    }

}
