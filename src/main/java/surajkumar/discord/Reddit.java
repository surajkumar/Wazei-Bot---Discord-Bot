package surajkumar.discord;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Submission;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Reddit {
    public static String getRandomMeme() {
        UserAgent userAgent = new UserAgent("WazeyBot", "dev.sk96.discord.bot", "1.0", "sk96_reddit");
        Credentials credentials = Credentials.script("sk96_reddit", "0x2B~|0x2b", "XNX_vFo6QRx7XQ", "aUPeaDGAxmKikmFd4kQcso-LLyc");
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient redditClient = OAuthHelper.automatic(adapter, credentials);
        DefaultPaginator<Submission> earthPorn = redditClient.subreddits("wtf", "memes").posts().build();
        List<String> images = new ArrayList<>();
        for (Submission s : earthPorn.next()) {
            if (!s.isSelfPost() && s.getUrl().contains(".jpg")) {
                images.add(s.getUrl());
            }
        }
        int random = ThreadLocalRandom.current().nextInt(images.size());
        return images.get(random);
    }

    public static String getNSFW() {
        UserAgent userAgent = new UserAgent("WazeyBot", "dev.sk96.discord.bot", "1.0", "sk96_reddit");
        Credentials credentials = Credentials.script("sk96_reddit", "0x2B~|0x2b", "XNX_vFo6QRx7XQ", "aUPeaDGAxmKikmFd4kQcso-LLyc");
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient redditClient = OAuthHelper.automatic(adapter, credentials);
        DefaultPaginator<Submission> earthPorn = redditClient.subreddits("NSFW_GIF", "NSFW_GIF").posts().build();
        List<String> images = new ArrayList<>();
        for (Submission s : earthPorn.next()) {
            if (!s.isSelfPost() && s.getUrl().contains(".gif")) {
                images.add(s.getUrl());
            }
        }
        int random = ThreadLocalRandom.current().nextInt(images.size());
        return images.get(random);
    }

    public static String getHentai() {
        UserAgent userAgent = new UserAgent("WazeyBot", "dev.sk96.discord.bot", "1.0", "sk96_reddit");
        Credentials credentials = Credentials.script("sk96_reddit", "0x2B~|0x2b", "XNX_vFo6QRx7XQ", "aUPeaDGAxmKikmFd4kQcso-LLyc");
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient redditClient = OAuthHelper.automatic(adapter, credentials);
        DefaultPaginator<Submission> earthPorn = redditClient.subreddits("AnimeHentaiGifs", "AnimeHentaiGifs").posts().build();
        List<String> images = new ArrayList<>();
        for (Submission s : earthPorn.next()) {
            if (!s.isSelfPost() && s.getUrl().contains(".gif")) {
                images.add(s.getUrl());
            }
        }
        int random = ThreadLocalRandom.current().nextInt(images.size());
        return images.get(random);
    }
}