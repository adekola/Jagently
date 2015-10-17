/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagently;

import static jagently.RandomQuotes.quoteArray;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author adekola
 */
public class FactsOfLife {

    public static enum FactTypes {

        WORLD, TECHNOLOGY, POLITICS, RANDOM, MOVIES
    }

    static final ArrayList<String[]> factsArray = new ArrayList<>();

    static final String[] worldFacts = new String[]{
        "Banging your head against a wall burns 150 calories an hour.\n", "In the UK, it is illegal to eat mince pies on Christmas Day!\n", "Pteronophobia is the fear of being tickled by feathers!\n", "When hippos are upset, their sweat turns red.\n", "A flock of crows is known as a murder.\n", "“Facebook Addiction Disorder” is a mental disorder identified by Psychologists.\n", "The average woman uses her height in lipstick every 5 years.\n", "29th May is officially “Put a Pillow on Your Fridge Day“.\n", "Cherophobia is the fear of fun.\n", "Human saliva has a boiling point three times that of regular water.\n", "If you lift a kangaroo’s tail off the ground it can’t hop.\n"
    };

    static final String[] techFacts = new String[]{
        "Hyphephilia are people who get aroused by touching fabrics.\n", "Billy goats urinate on their own heads to smell more attractive to females.\n", "The person who invented the Frisbee was cremated and made into frisbees after he died!\n", "During your lifetime, you will produce enough saliva to fill two swimming pools.\n", "An eagle can kill a young deer and fly away with it.\n", "Polar bears can eat as many as 86 penguins in a single sitting.\n", "King Henry VIII slept with a gigantic axe beside him.\n", "Bikinis and tampons invented by men.\n", "If Pinokio says “My Noes Will Grow Now”, it would cause a paradox. Details here.\n", "Heart attacks are more likely to happen on a Monday.\n"
    };

    static final String[] politicFacts = new String[]{
        "If you consistently fart for 6 years & 9 months, enough gas is produced to create the energy of an atomic bomb!\n", "An average person’s yearly fast food intake will contain 12 pubic hairs.\n", "The top six foods that make your fart are beans, corn, bell peppers, cauliflower, cabbage and milk!\n", "There is a species of spider called the Hobo Spider.\n", "‘Penis Fencing’ is a scientific term for the mating ritual between flatworms. It involves two flatworms attempting to stab the other flatworm with their penis.\n", "A toaster uses almost half as much energy as a full-sized oven.\n", "A baby spider is called a spiderling.\n", "You cannot snore and dream at the same time.\n", "The following can be read forward and backwards: Do geese see God?\n", "A baby octopus is about the size of a flea when it is born.\n", "A sheep, a duck and a rooster were the first passengers in a hot air balloon.\n", "In Uganda, 50% of the population is under 15 years of age.\n"
    };

    static final String[] randomFacts = new String[]{
        "Hitler’s mother considered abortion but the doctor persuaded her to keep the baby.\n", "Arab women can initiate a divorce if their husbands don’t pour coffee for them.\n", "Recycling one glass jar saves enough energy to watch TV for 3 hours.\n", "Smearing a small amount of dog feces on an insect bite will relieve the itching and swelling.\n", "Catfish are the only animals that naturally have an odd number of whiskers.\n", "Facebook, Skype and Twitter are all banned in China.\n", "95% of people text things they could never say in person.\n", "The Titanic was the first ship to use the SOS signal.\n", "In Poole, ‘Pound World’ went out of business because of a store across the road called ’99p Stores’, which was selling the same products but for just 1 pence cheaper! Read More.\n"
    };

    static final String[] movieFacts = new String[]{
        "About 8,000 Americans are injured by musical instruments each year.\n", "The French language has seventeen different words for ‘surrender’.\n", "Nearly three percent of the ice in Antarctic glaciers is penguin urine.\n", "Bob Dylan’s real name is Robert Zimmerman.\n", "A crocodile can’t poke its tongue out :p\n", "Sea otters hold hands when they sleep so they don’t drift away from each other.\n", "A small child could swim through the veins of a blue whale.\n", "Bin Laden’s death was announced on 1st May 2011. Hitler’s death was announced on 1st May 1945."
    };

    static {
        factsArray.add(techFacts);
        factsArray.add(movieFacts);
        factsArray.add(politicFacts);
        factsArray.add(randomFacts);
        factsArray.add(worldFacts);
    }

    public static String getFact(FactTypes type) {
        Random random = new Random();
        String[] arr = factsArray.get(type.ordinal());
        System.out.println(type.ordinal());
        return arr[random.nextInt(arr.length - 1)];
    }
}
