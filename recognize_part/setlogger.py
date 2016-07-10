#-*- coding:utf-8 -*-

import logging,logging.handlers
import os

def initlog(logfile, logger_name, level=logging.DEBUG):
    """ init log. """
    logger =  logging.getLogger(logger_name)
    if logfile[0] == '/':
       logsdir, logsfile = os.path.split(logfile)
       if not os.path.exists(logsdir):
	  os.makedirs(logsdir, mode = 0775)
    else:
       logsdir, logsfile = os.path.split(sys.path[0] + '/' + logfile)
       if not os.path.exists(logsdir):
	  os.makedirs(logsdir, mode = 0775)
       #hdlr = logging.FileHandler(logfile)
    hdlr = logging.handlers.RotatingFileHandler(logfile, maxBytes=1024*1024*1024, backupCount=5)
    formatter = logging.Formatter('[%(levelname)s %(asctime)s @ %(process)d] - %(message)s')
    hdlr.setFormatter(formatter)
    logger.addHandler(hdlr)
    logger.setLevel(level)
    return logger
