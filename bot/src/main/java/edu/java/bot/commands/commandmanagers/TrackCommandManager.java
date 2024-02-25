package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOfClient;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import edu.java.bot.utilities.parser.GitHubLinkParser;
import edu.java.bot.utilities.parser.LinkDetector;
import edu.java.bot.utilities.parser.StackOfLinkParser;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("MissingSwitchDefault")
public class TrackCommandManager implements CommandManager {

    private static final String LINK_ADDED_SUCCESSFULLY = "Link added successfully";
    private static final String LINK_ALREADY_EXISTS =
        "Link is already in track list. Check your current list using /list command";
    private static final String COMMAND_NAME = "/track";
    private final TelegramBot bot;
    private final UserRepository userRepository;
    private final UserNotRegisteredResponse userNotRegisteredResponse;
    private final TrackCommand trackCommand;
    private final GitHubClient gitHubClient;
    private final StackOfClient stackOfClient;

    @Autowired TrackCommandManager(
        TelegramBot bot,
        UserRepository userRepository,
        UserNotRegisteredResponse userNotRegisteredResponse,
        TrackCommand trackCommand,
        GitHubClient gitHubClient,
        StackOfClient stackOfClient
    ) {
        this.bot = bot;
        this.userRepository = userRepository;
        this.userNotRegisteredResponse = userNotRegisteredResponse;
        this.trackCommand = trackCommand;
        this.gitHubClient = gitHubClient;
        this.stackOfClient = stackOfClient;

    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void startProcess(Message message) {
        Long chatId = message.chat().id();
        User currentUser = userRepository.get(chatId);

        if (currentUser == null) {
            userNotRegisteredResponse.send(chatId);
        } else {
            if (message.text().equals(COMMAND_NAME)) {
                bot.execute(new SendMessage(chatId, "Please enter your link").replyMarkup(new ForceReply()));
            } else {

                String currentLinkUrl = message.text();
                LinkDetector.LinkType linkType = LinkDetector.detectLinkType(currentLinkUrl);

                switch (linkType) {
                    case GITHUB -> {
                        GitHubLinkParser gitHubParser = new GitHubLinkParser();
                        Map<String, String> gitHubData = gitHubParser.parse(currentLinkUrl);
                        for (String owner : gitHubData.keySet()) {
                            String repo = gitHubData.get(owner);
                            String json = gitHubClient.getRepository(owner, repo);
                            Link link = new Link(currentLinkUrl, json);
                            boolean linkExists = currentUser.checkLink(link);
                            if (linkExists) {
                                bot.execute(new SendMessage(chatId, LINK_ALREADY_EXISTS));
                            } else {
                                trackCommand.execute(currentUser, link);
                                bot.execute(new SendMessage(chatId, LINK_ADDED_SUCCESSFULLY));
                            }
                        }
                    }
                    case STACK_OVERFLOW -> {
                        StackOfLinkParser stackParser = new StackOfLinkParser();
                        String questionId = stackParser.parse(currentLinkUrl);
                        String json = stackOfClient.getQuestion(questionId, "stackoverflow");
                        Link link = new Link(currentLinkUrl, json);
                        boolean linkExists = currentUser.checkLink(link);
                        if (linkExists) {
                            bot.execute(new SendMessage(chatId, LINK_ALREADY_EXISTS));
                        } else {
                            trackCommand.execute(currentUser, link);
                            bot.execute(new SendMessage(chatId, LINK_ADDED_SUCCESSFULLY));
                        }
                    }
                    case UNKNOWN -> {
                        bot.execute(new SendMessage(
                            chatId,
                            "Unsupported link. Github and StackOverflow are only allowed."
                        ));
                    }
                }
            }
        }
    }
}
