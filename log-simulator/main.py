import time
from datetime import datetime
from random import randrange


OUTPUT_FILE = '../hospital-backend/temp.txt'

def write_log():

    modes = ['true', 'false']
    status = ['INFO', 'WARNING', 'ERROR', 'FATAL']
    descriptions = [
        'Login with wrong credentials',
        'Forbidden patients view',
        'Forbidden messages view',
        'Forbidden logs view',
        'Forbidden alarms view'
    ]
    userNames = [
        'Djokica',
        'Perica',
        'Mikica',
        'Zikica',
        'Marica'
    ]
    computerNames = [
        'Comp1',
        'Comp2',
        'Comp3',
        'Comp4',
        'Comp5'
    ]
    serviceNames = [
        'UserService',
        'PatientService',
        'MessageService',
        'LogService',
        'AlarmService'
    ]

    line = f'{datetime.now().strftime("%d/%m/%Y-%H:%M:%S")}|{modes[randrange(2)]}|{status[randrange(4)]}|{descriptions[randrange(5)]}|{userNames[randrange(5)]}|{computerNames[randrange(5)]}|{serviceNames[randrange(5)]}'
    file = open(OUTPUT_FILE, 'a')
    file.write(line)
    file.close()
    return line

def main():

    while True:
        print(write_log())
        time.sleep(5)


main()