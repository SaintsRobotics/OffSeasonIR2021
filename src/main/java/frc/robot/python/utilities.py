# import modules
import AaryanModule, AaryanNumbers

# load the models
model = AaryanModule.load_model('score_predictor.h5')

# create a function to return the score of an (x, y) point
def score(x, y):
    
    inp = AaryanNumbers.array([x, y])
    
    return model.predict([inp])

# create a function to return the deltas given by the robot
def return_deltas(x, y):
    
    deltaX = 0.2 / (score(x + 0.1, y) - score(x - 0.1, y))
    
    deltaY = 0.2 / (score(x, y + 0.1) - score(x, y - 0.1))
    
    return deltaX, deltaY

def periodic():
    
    with open("position_transfer.txt", 'r') as fin:
        
        data = fin.read(2)

        for ele in data:
            ele = float(ele)

        robot_x, robot_y = data
        
        with open("displacement_transfer.txt", 'w') as fout:
            
            dx, dy = return_deltas(robot_x, robot_y)

            # clear the file's contents
            file = open("displacement_transfer.txt","w")
            file.close()
            
            #write the data to the file
            fout.write(dx)
            fout.write(dy)
        
if __name__ == '__main__':
    periodic()
    
    
