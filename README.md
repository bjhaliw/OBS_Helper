# OBS_Helper
Helper program for overlaying text on screen through OBS. Lets the user create and display text on the screen for information such as a countdown timer, how long the stream has been running for, and what song is currently playing.

# Current Features

### Countdown Timer
- Input hours, minutes, or seconds into applicable fields
- Also use buttons to increment/decrement values by 1 or 5
- Saves current countdown time to a textfile to be read by OBS

### Stopwatch
- Keep track of how long you have been streaming with this stopwatch
- Displays to stopwatch.txt file for easy overlay through OBS
- Pause the stopwatch keeping the current time, or reset it back to 0

### VLC Song Selection

- Current song title is pulled from VLC HTTP player and displayed in text file
- Username/Login fields to login to localhost:8080
- Allow for user to select the formatting of the text file by using markdown tags
  - Tags: [artist], [title], [genre], [album], [copyright], [track number], [date], [filename]
- If no tags are found in the desired format, the default is the filename of the song and that will be displayed instead
- If some tags are not found, those tags will be omitted with an empty string which may cause undesired formatting
- Example 1: User is listening to the song "One" by Metallica. 
  - User selects [artist] - [title] from the ComboBox and the tags are found in the HTML code 
  - Text document will then display: Metallica - One  
- Example 2: User is listening to the song "Another Brick in the Wall" by Pink Floyd
  - User types in Now Playing: [title] - [album], but the [album] tag is not found in the HTML code
  - Text document will then display: Now Playing: Another Brick in the Wall -
- Example 3: User is listening to "Living on a Prayer" by Bon Jovi
  - User selects the [title] - [track number] tags from the ComboBox, but neither of the tags are found in the HTML code
  - Text document will then display the filename of the song: bonjovi.mp3

### Light/Dark Mode

- Allows the user to swap easily between the two different modes from the File menu

# In-Work Features
- Overlay for music currently being played
  - Local files such as mp3 easily implemented, but harder for other
  - Want to get Spotify, YouTube, and other music streaming services

- Custom formatting for the countdown and stopwatch text files

- How to Use tutorial menu

  
# Future Features
- Quick Scene Changer
- Save preferences for next time use
- Custom URL selection and port for the VLC HTTP player
- Create custom text documents with desired text to also be displayed

# Example Photos
![Time](https://github.com/bjhaliw/OBS_Helper/blob/main/Example%20Photos/time.png)
![Music](https://github.com/bjhaliw/OBS_Helper/blob/main/Example%20Photos/musictab.png)
