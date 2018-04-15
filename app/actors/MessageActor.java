package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

public class MessageActor extends UntypedActor {
    //Self - Reference the Actor
    //  PROPS
    //Object of feed service
    //object of newsAgentService
    //Define another actor Reference

    private final ActorRef out;
    private FeedService feedService = new FeedService();
    private FeedResponse feedResponse = new FeedResponse();
    private NewsAgentService newsAgentService = new NewsAgentService();
    private NewsAgentResponse newsAgentResponse = new NewsAgentResponse();

    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);

    }

    public MessageActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        Message messageObject = new Message();
        if (message instanceof String)

        {

            messageObject.text = message. toString();
            messageObject.sender = Message.Sender.USER;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
            String query = newsAgentService.getNewsAgentResponse("Find " + message, UUID.randomUUID()).query;
            feedResponse = feedService.getFeedByQuery(query); // to fetch the news
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + newsAgentResponse.query;
            messageObject.feedResponse = feedResponse;
            messageObject.sender = Message.Sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
        }
        else {
            messageObject.text = "Input is invalid";
            messageObject.sender = Message.Sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
        }

    }


}