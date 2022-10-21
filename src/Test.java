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


        //Period p = new Period

        Collection<Track> tracks = User.getTopTracks(user, Period.TWELVE_MONTHS, key);




        DateFormat format = DateFormat.getDateInstance();
        String from = format.format(chart.getFrom());
        String to = format.format(chart.getTo());

        s.nextLine();
        //System.out.printf("Charts for %s for the week from %s to %s:%n", user, from, to);

        Collection<Artist> artists = chart.getEntries();



        System.out.println("Enter for Top 5 songs");
        s.nextLine();


        String[] topTrackNames = new String[50];

        int k=0;
        for (Track track : tracks) {
            System.out.println(track.getName());
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

        System.out.println("Next line for Artists");

        for (int i=0; i<5; i++){
            for (Artist artist : artists) {
                System.out.println(artist.getName());
            }
        }



    }
}
