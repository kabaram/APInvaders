import javax.sound.midi.*;

public class Sound
{
   private static MidiChannel[] channels=null;		//MIDI channels
   private static Instrument[] instr;					//MIDI instrument bank
   private static final int PIANO = 0;              //here is the list of all instrument sounds 
   private static final int MUTED_GT = 28;
   private static final int TIMPANI = 47;
   private static final int ORCH_HIT = 55;
   private static final int FRET_NOISE = 120;       //             https://www.midi.org/specifications-old/item/gm-level-1-sound-set
   private static final int GUNSHOT = 127;
   private static final boolean soloSound = true;     //if we want only one sound to trigger at a time

   public static void initialize()
   {
      try 
      {
         Synthesizer synth = MidiSystem.getSynthesizer();
         synth.open();
         Sound.channels = synth.getChannels();
         Sound.instr = synth.getDefaultSoundbank().getInstruments();
      }
      catch (Exception ignored) 
      {}
      channels[0].programChange(instr[PIANO].getPatch().getProgram());
      silence();		
   }

   //turn sounds off 
   public static void silence()
   {
      channels[0].allNotesOff();		
   }

   //sound for cannon shooting missile
   public static void cannonShoot()
   {
      if(soloSound)
         silence();
      channels[0].programChange(instr[FRET_NOISE].getPatch().getProgram());
      int pitch = (int)(Math.random()*6)+45;          
      int velocity = (int)(Math.random()*10)+30;
      channels[0].noteOn(pitch, velocity);
   }
   
   //sound for cannon getting hit
   public static void cannonHit()
   {
      if(soloSound)
         silence();
      channels[0].programChange(instr[GUNSHOT].getPatch().getProgram());
      int pitch = (int)(Math.random()*6)+30;          
      int velocity = (int)(Math.random()*10)+50;
      channels[0].noteOn(pitch, velocity);
   }
   
   //sound for clearing the round
   public static void win()
   {
      if(soloSound)
         silence();
      channels[0].programChange(instr[ORCH_HIT].getPatch().getProgram());
      int pitch = 42;          
      int velocity = 100;
      channels[0].noteOn(pitch, velocity);
   }
   
   //sound for game over
   public static void gameOver()
   {
      if(soloSound)
         silence();
      channels[0].programChange(instr[PIANO].getPatch().getProgram());
      int pitch = 22;          
      int velocity = 60;
      channels[0].noteOn(pitch, velocity);
   }
   
   //sound for alien getting hit
   public static void alienHit()
   {
      if(soloSound)
         silence();
      channels[0].programChange(instr[MUTED_GT].getPatch().getProgram());
      int pitch = (int)(Math.random()*6)+15;          
      int velocity = (int)(Math.random()*10)+60;
      channels[0].noteOn(pitch, velocity);
   }
}