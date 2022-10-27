import de.umass.lastfm.*;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Scanner;

public class Test {
    public static void main(String [] args){
        String key = "5a78b2b840811b4ab8d26309e2b68ee6"; //this is the key used in the Last.fm API examples
        Scanner s = new Scanner(System.in);
        String user = "Roy19110";

        System.out.println("What is your Last FM Name?");
        user = s.nextLine();
        Chart<Artist> chart = User.getWeeklyArtistChart(user, 5, key);
        System.out.println("What period of time would you like to observe?");
        System.out.println("A: Last week  B: Last month  C: Last 3 months  D: Last 6 months  E: Last year  F: Overall");
        period = s.nextLine();

        Collection<Artist> Artists = User.getTopArtists(user, Period.OVERALL, key);
        Collection<Track> tracks = User.getTopTracks(user, Period.OVERALL, key);
        Collection<Album> albums = User.getTopAlbums(user, Period.OVERALL, key);


        if(period.equals("A")){
            Artists = User.getTopArtists(user, Period.WEEK, key);
            tracks = User.getTopTracks(user, Period.WEEK, key);
            albums = User.getTopAlbums(user, Period.WEEK, key);
            period = "last week";
        } else if(period.equals("B")){
            Artists = User.getTopArtists(user, Period.ONE_MONTH, key);
            tracks = User.getTopTracks(user, Period.ONE_MONTH, key);
            albums = User.getTopAlbums(user, Period.ONE_MONTH, key);
            period = "last month";
        } else if (period.equals("C")) {
            Artists = User.getTopArtists(user, Period.THREE_MONTHS, key);
            tracks = User.getTopTracks(user, Period.THREE_MONTHS, key);
            albums = User.getTopAlbums(user, Period.THREE_MONTHS, key);
        } else if(period.equals("D")) {
            Artists = User.getTopArtists(user, Period.SIX_MONTHS, key);
            tracks = User.getTopTracks(user, Period.SIX_MONTHS, key);
            albums = User.getTopAlbums(user, Period.SIX_MONTHS, key);
            period = "last six months";
        }
        else if(period.equals("E")) {
            Artists = User.getTopArtists(user, Period.TWELVE_MONTHS, key);
            tracks = User.getTopTracks(user, Period.TWELVE_MONTHS, key);
            albums = User.getTopAlbums(user, Period.TWELVE_MONTHS, key);
            period = "last year";
        }
        else if(period.equals("F")) {
            Artists = User.getTopArtists(user, Period.OVERALL, key);
            tracks = User.getTopTracks(user, Period.OVERALL, key);
            albums = User.getTopAlbums(user, Period.OVERALL, key);
            period = "overall";
        }




        //Period p = new Period



        DateFormat format = DateFormat.getDateInstance();
        String from = format.format(chart.getFrom());
        String to = format.format(chart.getTo());

        s.nextLine();
        //System.out.printf("Charts for %s for the week from %s to %s:%n", user, from, to);

        Collection<Artist> weeklyArtists = chart.getEntries();



        System.out.println("Enter for your Top 5 songs of " + period);
        s.nextLine();


        String[] topTrackNames = new String[50];
        int k=0;
        for (Track track : tracks) {
            System.out.println(track.getName());
        }

        String[] topArtistNames = new String[50];
        int p=0;
        for (Artist artist : Artists) {
            //System.out.println(track.getName());
            topArtistNames[p] = artist.getName();
            p++;
        }

        String[] topAlbumNames = new String[50];
        int u = 0;
        for(Album album : albums){
            topAlbumNames[u] = album.getName() + " by " + album.getArtist();
           u++;
        }

        int l=0;
        int[] topTrackPlaycount = new int[50];
        for (Track Track: tracks){
            topTrackPlaycount[l] = Track.getPlaycount();
            l++;
        }




        for (int i=0; i<5; i++){
            System.out.println(topTrackNames[i] + " was played " + topTrackPlaycount[i] + " times");
        }

        System.out.println();
        System.out.println();
        System.out.println("Next line for your Top 5 Artists of " + period);
        System.out.println("------------------------------------------------------------");

        s.nextLine();
        for (int i=0; i<5; i++){
            System.out.println(topArtistNames[i]);
        }

        System.out.println();
        System.out.println();
        System.out.println("Next line for your Top 5 ALBUMS of " + period);
        System.out.println("------------------------------------------------------------");

        s.nextLine();
        for (int i=0; i<5; i++){
            System.out.println(topAlbumNames[i]);
        }

    }
}
