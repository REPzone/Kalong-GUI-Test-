package core;

/**
 * Created by biospore on 9/11/15 (2:19 AM).
 */


import java.util.Base64;
import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAnimeListAPI implements IMyAnimeListAPI {
    private String login;
    private String encodedProfile;
    private StringBuilder sb;
    private final IManager manager;
    private ITask currentTask;

    public MyAnimeListAPI(IManager manager){
        this.sb = new StringBuilder();
        this.manager = manager;
        this.currentTask = null;
    }


    public String verifyProfile() throws IOException {
        this.sb.delete(0, this.sb.length());
        this.sb.append("http://myanimelist.net/api/account/verify_credentials.xml");
        HttpURLConnection conn = this.getConnection(sb.toString());
        getConnection("GET");
        this.sb.delete(0, this.sb.length());
        sb.append("Basic ");
        sb.append(this.encodedProfile);
        conn.setRequestProperty("Authorization", sb.toString());

        return this.getAnswer(conn);
    }

    @Override
    public void setProfile(IProfile profile) {
        this.login = profile.getLogin();
        this.sb.delete(0, this.sb.length());
        this.sb.append(profile.getLogin());
        this.sb.append(":");
        this.sb.append(profile.getPassword());
        this.encodedProfile = Base64.getEncoder().encodeToString(this.sb.toString().getBytes());
    }

    @Override
    public String searchEntry(String name, String type) throws IOException{
        this.sb.delete(0, this.sb.length());
        sb.append("http://myanimelist.net/api/");
        sb.append(type);
        sb.append("/search.xml?q=");
        sb.append(name.replace(" ", "+"));

        HttpURLConnection conn = this.getConnection(sb.toString());

        conn.setRequestMethod("GET");
        this.sb.delete(0, this.sb.length());
        sb.append("Basic ");
        sb.append(this.encodedProfile);
        conn.setRequestProperty("Authorization", sb.toString());

        return this.getAnswer(conn);
    }

    @Override
    public String getList(String type) throws IOException {
        this.sb.delete(0, this.sb.length());
        sb.append("http://myanimelist.net/malappinfo.php?u=");
        sb.append(this.login);
        sb.append("&status=all&type=");
        sb.append(type);
        HttpURLConnection conn = this.getConnection(sb.toString());

        conn.setRequestMethod("GET");

        return this.getAnswer(conn);
    }

    @Override
    public void addEntry(long id, String data, String type) {
        this.sb.delete(0, this.sb.length());
        sb.append("http://myanimelist.net/api/");
        sb.append(type);
        sb.append("list/add/");
        sb.append(id);
        sb.append(".xml");

    }

    @Override
    public void updateEntry(long id, String data, String type) {
        this.sb.delete(0, this.sb.length());
        sb.append("http://myanimelist.net/api/");
        sb.append(type);
        sb.append("list/add/");
        sb.append(id);
        sb.append(".xml");

    }

    @Override
    public void deleteEntry(long id, String data, String type) {
        this.sb.delete(0, this.sb.length());
        sb.append("http://myanimelist.net/api/");
        sb.append(type);
        sb.append("list/add/");
        sb.append(id);
        sb.append(".xml");

    }

    protected String getAnswer(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputs;
            this.sb.delete(0, this.sb.length());
            while ((inputs = br.readLine()) != null){
                sb.append(inputs);
            }
            br.close();
        }
        else{
            throw new HTTPException(conn.getResponseCode());
        }
        return sb.toString();
    }

    protected HttpURLConnection getConnection(String url) throws IOException {
        URL uaddr = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) uaddr.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(false);
        conn.setRequestProperty("User-Agent", "kalong/0.1.0");
        return conn;
    }

    @Override
    public void run() {
            synchronized (this.manager){
                if (!this.manager.isEmpty()){
                    currentTask = this.manager.getTask();
                }
            }
        if (currentTask != null){
            System.out.println("x");
        }
    }
}
