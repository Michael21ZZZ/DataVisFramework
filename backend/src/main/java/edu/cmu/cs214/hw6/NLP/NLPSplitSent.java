package edu.cmu.cs214.hw6.NLP;
import edu.stanford.nlp.pipeline.*;

import java.util.*;

public class NLPSplitSent {
    
    public static String text = """
        The American Civil War (April 12, 1861 – May 26, 1865; also known by other names) was a civil war in the United States. 
        It was fought between the Union[f] (the North) and the Confederacy (the South), the latter formed by states that had seceded. 
        The central cause of the war was the dispute over whether slavery would be permitted to expand into the western territories, leading to more slave states, or be prevented from doing so, which was widely believed would place slavery on a course of ultimate extinction. 
        Decades of political controversy over slavery were brought to a head by the victory in the 1860 U.S. presidential election of Abraham Lincoln, who opposed slavery's expansion into the west. 
        An initial seven southern slave states responded to Lincoln's victory by seceding from the United States and, in 1861, forming the Confederacy. 
        The Confederacy seized U.S. forts and other federal assets within their borders. 
        Led by Confederate President Jefferson Davis, the Confederacy ultimately came to control over half of U.S. territory in eleven of the 34 U.S. states that then existed. 
        Four years of intense combat, mostly in the South, ensued. 
        During 1861–1862 in the war's Western Theater, the Union made significant permanent gains—though in the war's Eastern Theater the conflict was inconclusive. 
        On January 1, 1863, Lincoln issued the Emancipation Proclamation, declaring all slaves in states in rebellion to be free, which made ending slavery a war goal. 
        To the west, the Union destroyed the Confederate's river navy by the summer of 1862, then much of its western armies, and seized New Orleans. 
        The successful 1863 Union siege of Vicksburg split the Confederacy in two at the Mississippi River. 
        In 1863, Confederate General Robert E. Lee's incursion north ended at the Battle of Gettysburg. 
        Western successes led to General Ulysses S. Grant's command of all Union armies in 1864. 
        Inflicting an ever-tightening naval blockade of Confederate ports, the Union marshaled resources and manpower to attack the Confederacy from all directions. 
        This led to the fall of Atlanta in 1864 to Union General William Tecumseh Sherman, followed by his march to the sea. The last significant battles raged around the ten-month Siege of Petersburg, gateway to the Confederate capital of Richmond. 
        The Confederates abandoned Richmond, and on April 9, 1865, Lee surrendered to Grant following the Battle of Appomattox Court House, setting in motion the end of the war. 
        A wave of Confederate surrenders followed. On April 14, just five days after Lee's surrender, Lincoln was assassinated. 
        As a practical matter, the war ended with the May 26 surrender of the Department of the Trans-Mississippi but the conclusion of the American Civil War lacks a clear and precise historical end date. 
        Confederate ground forces continued surrendering past the May 26 surrender date until June 23. 
        By the end of the war, much of the South's infrastructure was destroyed, especially its railroads.
        The Confederacy collapsed, slavery was abolished, and four million enslaved black people were freed. 
        The war-torn nation then entered the Reconstruction era in an attempt to rebuild the country, bring the former Confederate states back into the United States, and grant civil rights to freed slaves.
        The Civil War is one of the most extensively studied and written about episodes in U.S. history. It remains the subject of cultural and historiographical debate. 
        Of particular interest is the persisting myth of the Lost Cause of the Confederacy. 
        The American Civil War was among the first wars to utilize industrial warfare. Railroads, the telegraph, steamships, the ironclad warship, and mass-produced weapons were all widely used during the war. 
        In total, the war left between 620,000 and 750,000 soldiers dead, along with an undetermined number of civilian casualties, making the Civil War the deadliest military conflict in American history.
        [g] The technology and brutality of the Civil War foreshadowed the coming World Wars.
      """;

    public static void main(String[] args) {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize, ssplit");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument doc = new CoreDocument(text);
        // annotate
        pipeline.annotate(doc);
        // display sentences
        for (CoreSentence sent : doc.sentences()) {
            System.out.println(sent.text());
        }
    }
}
