import de.umass.lastfm.*;

import java.io.IOException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Scanner;

public class Test {
    static Scanner s = new Scanner(System.in);


    public static int getNum(){
        try{ return (Integer.parseInt(s.nextLine()));}
        catch(Exception e){return -1;}
    }

    public static void main(String[] args){
        int valid = -1;
        while(valid == -1) {
            //valid is an int for the while loop. The while loop makes sure the try catch continues running until it works. If try doesn't completely run,
            //catch will make sure valid = -1 and while loop will keep running
            //if try runs through completely, it will set valid = 1 and while loop will finish running.
            //This while loop with int valid is to make sure program doesn't crash if we enter an invalid username
            try {
                String key = "5a78b2b840811b4ab8d26309e2b68ee6"; //this is the key used in the Last.fm API examples
                Scanner s = new Scanner(System.in);
                String user = "Roy19110"; //initializes user and period.
                String period = "";


                System.out.println("What is your Last FM Name?"); //EX USERNAMES: "Roy19110"   "fales_"   "usrname7"
                user = s.nextLine();

                System.out.println("What period of time would you like to observe?");
                System.out.println("A: Last week  B: Last month  C: Last 3 months  D: Last 6 months  E: Last year  F: Overall");
                period = s.nextLine();


                while (!(period.equals("A") || period.equals("a") || period.equals("B") || period.equals("b") || period.equals("C") ||
                        period.equals("c") || period.equals("D") || period.equals("d") || period.equals("E") || period.equals("e") || period.equals("F") || period.equals("f"))) {
                    //checks for valid input, if not one of the given options(not case sensitive) asks for different input
                    System.out.println("Try again with valid input!");
                    System.out.println("What period of time would you like to observe?");
                    System.out.println("A: Last week  B: Last month  C: Last 3 months  D: Last 6 months  E: Last year  F: Overall");
                    period = s.nextLine();
                }

                Collection<Artist> Artists = User.getTopArtists(user, Period.OVERALL, key);
                Collection<Track> tracks = User.getTopTracks(user, Period.OVERALL, key);
                Collection<Album> albums = User.getTopAlbums(user, Period.OVERALL, key);
                Collection<Artist> CountryA = Geo.getTopArtists("United States", key);
                Collection<Track> CountryT = Geo.getTopTracks("United States", key);

                System.out.println("Checking if username exists and period works...");

                ArrayList<Artist> check = new ArrayList<>(Artists);
                Artist checkCrash = check.get(0);
                System.out.println("Username exists for period: " + user);

                //program will set user's top artists, tracks, and albums based on the
                //time frame of the period they chose.
                if (period.equals("A") || period.equals("a")) {
                    Artists = User.getTopArtists(user, Period.WEEK, key);
                    tracks = User.getTopTracks(user, Period.WEEK, key);
                    albums = User.getTopAlbums(user, Period.WEEK, key);
                    period = "last week";
                } else if (period.equals("B") || period.equals("b")) {
                    Artists = User.getTopArtists(user, Period.ONE_MONTH, key);
                    tracks = User.getTopTracks(user, Period.ONE_MONTH, key);
                    albums = User.getTopAlbums(user, Period.ONE_MONTH, key);
                    period = "last month";
                } else if (period.equals("C") || period.equals("c")) {
                    Artists = User.getTopArtists(user, Period.THREE_MONTHS, key);
                    tracks = User.getTopTracks(user, Period.THREE_MONTHS, key);
                    albums = User.getTopAlbums(user, Period.THREE_MONTHS, key);
                    period = "Last 3 months";
                } else if (period.equals("D") || period.equals("d")) {
                    Artists = User.getTopArtists(user, Period.SIX_MONTHS, key);
                    tracks = User.getTopTracks(user, Period.SIX_MONTHS, key);
                    albums = User.getTopAlbums(user, Period.SIX_MONTHS, key);
                    period = "last six months";
                } else if (period.equals("E") || period.equals("e")) {
                    Artists = User.getTopArtists(user, Period.TWELVE_MONTHS, key);
                    tracks = User.getTopTracks(user, Period.TWELVE_MONTHS, key);
                    albums = User.getTopAlbums(user, Period.TWELVE_MONTHS, key);
                    period = "last year";
                } else if (period.equals("F") || period.equals("f")) {
                    Artists = User.getTopArtists(user, Period.OVERALL, key);
                    tracks = User.getTopTracks(user, Period.OVERALL, key);
                    albums = User.getTopAlbums(user, Period.OVERALL, key);
                    period = "overall";
                }


                System.out.println("Enter for your Top 5 songs of " + period);
                s.nextLine();


                //These for each loops take the collection datatypes and transfer them into String arrays for easy access when printing later on
                String[] countryArtists = new String[50];
                int z = 0;
                for (Artist artist : CountryA) {
                    countryArtists[z] = artist.getName();
                    z++;
                }

                String[] countryTracks = new String[50];
                int q = 0;
                for (Track track : CountryT) {
                    countryTracks[q] = track.getName();
                    q++;
                }

                String[] topTrackNames = new String[50];
                int k = 0;
                for (Track track : tracks) {
                    topTrackNames[k] = track.getName();
                    k++;
                }

                String[] topArtistNames = new String[50];

                //Here we are converting the top artists of the user from a collection into an Arraylist,
                //so that we can iterate specific intervals of top artists
                //We are doing the same for recommendedArtists, which is an arraylist of the similar artists
                //to a specific artist in top artists arraylist
                ArrayList<Artist> userArtists = new ArrayList<>();
                for (Artist artist : Artists) {
                    //System.out.println(track.getName());
                    userArtists.add(artist);
                }

                ArrayList<Artist> topRecArtists = new ArrayList<>();

                //count1 keeps track of while loop to make sure it stops once topRecTracks contains 5 recommended songs
                int count1 = 0;
                //artistCount iterates through songs within the arraylist of user's top tracks
                int artistCount = 0;

                while (count1 < 20) {
                    Artist tempArtist = userArtists.get(artistCount);
                    Collection<Artist> recArtistCollection = tempArtist.getSimilar(tempArtist.getName(), 21, key);

                    //changing the generated collection of recommended songs into an arraylist to make it more easily accessible
                    ArrayList<Artist> recommendedArtists = new ArrayList<>(recArtistCollection);

                    //large if statement is to make sure that firstly, the recommendedTracks array is not null
                    if (recommendedArtists.size() > 0) {
                        //this int iterates through the tracks in the recommended arraylist
                        int recArtistCount = 0;

                        //this boolean keeps track whether the current recommended song is the same song as any of the user's top songs
                        boolean sameAsTopArtists = false;

                        //for each loop to track if current rec song is the same as a song in top songs
                        //if boolean is true, that means current rec song also appears in user's top songs
                        for (Artist artist : Artists) {
                            if (artist.getName().equals(recommendedArtists.get(recArtistCount).getName())) {
                                sameAsTopArtists = true;
                            }
                        }

                        //keeps looping through recommended songs by adding to recTrackCount until boolean is false
                        while (sameAsTopArtists == true) {
                            recArtistCount++;
                            sameAsTopArtists = false;
                            for (Artist artist : Artists) {
                                if (artist.getName().equals(recommendedArtists.get(recArtistCount).getName())) {
                                    sameAsTopArtists = true;
                                }
                            }

                            //also making sure that the song recommended is a song that has already been recommended
                            for (Artist name : topRecArtists) {
                                if (name.getName().equals(recommendedArtists.get(recArtistCount).getName())) {
                                    sameAsTopArtists = true;
                                }
                            }

                        }
                        //only when boolean is false (rec song is not the same as a top song) do we then add that rec song we generated
                        //into the topRecTracks arraylist
                        topRecArtists.add(recommendedArtists.get(recArtistCount));
                        count1++;
                    }

                    artistCount++;
                }


                //--------------------------------------------------------------------------------------------------------------------------------------


                //converting the top tracks of user from collection to arraylist
                ArrayList<Track> userTracks = new ArrayList<>();
                for (Track track : tracks) {
                    //System.out.println(track.getName());
                    userTracks.add(track);
                }

                ArrayList<Track> topRecTracks = new ArrayList<>();

                //completely similar and basically the same as code for recommended Artists, except for songs
                //count keeps track of while loop to make sure it stops once topRecTracks contains 5 recommended songs
                int count = 0;
                //trackCount iterates through songs within the arraylist of user's top tracks
                int trackCount = 0;

                while (count < 20) {
                    Track tempTrack = userTracks.get(trackCount);
                    Collection<Track> recTrackCollection = tempTrack.getSimilar(tempTrack.getArtist(), tempTrack.getName(), key);


                    //changing the generated collection of recommended songs into an arraylist to make it more easily accessible
                    ArrayList<Track> recommendedTracks = new ArrayList<>(recTrackCollection);

                    //large if statement is to make sure that firstly, the recommendedTracks array is not null
                    if (recommendedTracks.size() > 0) {
                        //this int iterates through the tracks in the recommended arraylist
                        int recTrackCount = 0;

                        //this boolean keeps track whether the current recommended song is the same song as any of the user's top songs
                        boolean sameAsTopSongs = false;

                        //for each loop to track if current rec song is the same as a song in top songs
                        //if boolean is true, that means current rec song also appears in user's top songs
                        for (Track track : tracks) {
                            if (track.getName().equals(recommendedTracks.get(recTrackCount).getName())) {
                                sameAsTopSongs = true;
                            }
                        }

                        //keeps looping through recommended songs by adding to recTrackCount until boolean is false
                        while (sameAsTopSongs == true) {
                            recTrackCount++;
                            sameAsTopSongs = false;
                            for (Track track : tracks) {
                                if (track.getName().equals(recommendedTracks.get(recTrackCount).getName())) {
                                    sameAsTopSongs = true;
                                }
                            }

                            //also making sure that the song recommended is a song that has already been recommended
                            for (Track name : topRecTracks) {
                                if (name.getName().equals(recommendedTracks.get(recTrackCount).getName())) {
                                    sameAsTopSongs = true;
                                }
                            }

                        }
                        //only when boolean is false (rec song is not the same as a top song) do we then add that rec song we generated
                        //into the topRecTracks arraylist
                        topRecTracks.add(recommendedTracks.get(recTrackCount));
                        count++;
                    }

                    trackCount++;
                }


                ArrayList<Album> topAlbums = new ArrayList<>(albums);


                int l = 0;
                int[] topTrackPlaycount = new int[50];
                int totalPlayCount = 0;
                for (Track Track : tracks) {
                    topTrackPlaycount[l] = Track.getPlaycount();
                    totalPlayCount += Track.getPlaycount();

                    //smilar to the other for each loops it takes the collection data type and makes it into a string array
                    //but with every addition we get the total play count and add it to total play count to use later

                    l++;
                }

                int p = 0;
                for (Artist artist : Artists) {
                    topArtistNames[p] = artist.getName();
                    p++;
                }

                //From here on out, it is printing out everything

                for (int i = 0; i < 5; i++) {
                    System.out.println(topTrackNames[i] + " was played " + topTrackPlaycount[i] + " times");
                }

                System.out.println();
                System.out.println();
                System.out.println("Press Enter for Top Artists");
                s.nextLine();
                System.out.println("Top 5 Artists of " + period);
                System.out.println("------------------------------------------------------------");

                for (int i = 0; i < 5; i++) {
                    System.out.println(topArtistNames[i]);
                }

                System.out.println();
                System.out.println();
                System.out.println("Press Enter for Top Albums");
                s.nextLine();
                System.out.println("Top 5 ALBUMS of " + period);
                System.out.println("------------------------------------------------------------");

                for (int i = 0; i < 5; i++) {
                    System.out.println(topAlbums.get(i).getName() + " by " + topAlbums.get(i).getArtist());
                }

                System.out.println();
                System.out.println();
                System.out.println("How many recommended artists do you want?");
                System.out.println("Options: 1-20");
                int recArtistNum = getNum();
                while (recArtistNum < 1 || recArtistNum > 20) {
                    System.out.println("Invalid input try again!");
                    System.out.println("How many recommended artists do you want?");
                    System.out.println("Options: 1-20");
                    recArtistNum = getNum();
                }
                System.out.println();
                System.out.println("Press Enter for Recommended Artists");
                s.nextLine();
                System.out.println("Recommended artists");
                System.out.println("------------------------------------------------------------");

                for (int i = 0; i < recArtistNum; i++) {
                    System.out.println(topRecArtists.get(i).getName());
                }

                System.out.println();
                System.out.println();
                System.out.println("How many recommended songs do you want?");
                System.out.println("Options: 1-20");
                int recSongNum = getNum();
                while (recSongNum < 1 || recSongNum > 20) {
                    System.out.println("Invalid input try again!");
                    System.out.println("How many recommended songs do you want?");
                    System.out.println("Options: 1-20");
                    recSongNum = getNum();
                }
                System.out.println();
                System.out.println("Press Enter for Recommended Songs");
                s.nextLine();
                System.out.println("Recommended songs");
                System.out.println("------------------------------------------------------------");

                for (int i = 0; i < recSongNum; i++) {
                    System.out.println(topRecTracks.get(i).getName() + " by " + topRecTracks.get(i).getArtist());
                }


                System.out.println();
                System.out.println();
                System.out.println("Press Enter for Fun Details");
                s.nextLine();
                System.out.println("Fun Details of " + period);
                System.out.println("------------------------------------------------------------");

                System.out.println("You've played your top 50 songs " + totalPlayCount + " times!");


                System.out.println();
                System.out.println();
                System.out.println("Press Enter for Top Tracks of the Country");
                s.nextLine();
                System.out.println("Did you know that the Top Tracks of the Country are:");
                System.out.println("------------------------------------------------------------");

                for (int i = 0; i < 5; i++) {
                    System.out.println(countryTracks[i]);
                }
                System.out.println();
                System.out.println();
                System.out.println("Press Enter for Top Artists of the Country");
                s.nextLine();
                System.out.println("Lastly, Did you know that the Top Artists of the Country are:");
                System.out.println("------------------------------------------------------------");

                for (int i = 0; i < 5; i++) {
                    System.out.println(countryArtists[i]);
                }


                System.out.println();
                System.out.println("Thank you for using Spotify Wrapped!");


                valid = 1;
            }
            catch (Exception e) {
                System.out.println("Invalid Username, please try in again");
                System.out.println();
                valid = -1;
            }
        }

    }
}
