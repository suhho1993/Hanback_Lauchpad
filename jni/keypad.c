#include<stdlib.h>
#include<string.h>
#include<sys/ioctl.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>
#include<unistd.h>
#include<linux/input.h>
#include <jni.h>
#include <errno.h>
#include <sys/mman.h>



#define EVENT_BUF_NUM 64
#define FPGA_BASEADDRESS 0x05000000
#define PIEZO OFFSET 0x50
#define KEY_COL_OFFSET 0x70
#define KEY_ROW_OFFSET 0x72

static int fd;
static unsigned short *addr_fpga;

JNIEXPORT jint JNICALL Java_JNI_Keypad_Open(JNIEnv* env, jobject thiz){
	int ret;
	fd=open("/dev/input/event2",O_RDONLY);
	if(fd<=0)return -errno;
	return fd;
}

JNIEXPORT jint JNICALL Java_JNI_Keypad_Close(JNIEnv* env, jobject thiz){

	munmap(addr_fpga,4096);
	if(fd>0){
		close(fd);
	}
	return 0;
}

JNIEXPORT jint JNICALL Java_JNI_Keypad_GetValue(JNIEnv* env, jobject thiz){
	
	int i;
	size_t read_bytes;
	struct input_event event_buf[EVENT_BUF_NUM];

	if(fd<0) fd=open("/dev/input/event2",O_RDONLY);
	if(fd<0) return -errno;


	read_bytes = read(fd, event_buf, (sizeof(struct input_event)*EVENT_BUF_NUM));

	if(read_bytes < sizeof(struct input_event)){
		return 55555;
	}

	for(i=0; i<(read_bytes/sizeof(struct input_event)); i++){
		if((event_buf[i].type == EV_KEY) && (event_buf[i].value == 0)){
			return event_buf[i].code;
		}
	}


	return -1;
	/*

	short value;

	unsigned short *keypad_row_addr, *keypad_col_addr, *piezo_addr;
	int i,quit=1;
	if((fd=open("/dev/mem",O_RDWR|O_SYNC))<0){
	perror("mem open fail\n");
	//exit(1);
	}
	addr_fpga=(unsigned short *) mmap(NULL,4096,PROT_WRITE|PROT_READ, MAP_SHARED,fd,FPGA_BASEADDRESS);
	keypad_col_addr=addr_fpga+KEY_COL_OFFSET/sizeof(unsigned short);
	keypad_row_addr=addr_fpga+KEY_ROW_OFFSET/sizeof(unsigned short);

	if(*keypad_row_addr == (unsigned short)-1 || *keypad_col_addr ==(unsigned short)-1){
	close(fd);
	//exit(1);
	}

	while(quit){
	*keypad_row_addr=0x01;
	usleep(1000);
	value=(*keypad_col_addr & 0x0f);
	*keypad_row_addr=0x00;
	switch(value){
	case 0x01: value=0x01; break;
	case 0x02: value=0x02; break;
	case 0x04: value=0x03; break;
	case 0x08: value=0x04; break;
	}
	if(value != 0x00)goto stop_poll;

	*keypad_row_addr=0x02;
	for(i=0;i<2000;i++);
	value=value|(*keypad_col_addr & 0x0f);
	*keypad_row_addr=0x00;
	switch(value){
	case 0x01: value=0x05; break;
	case 0x02: value=0x06; break;
	case 0x04: value=0x07; break;
	case 0x08: value=0x08; break;
	}
	if(value!=0x00)goto stop_poll;

	*keypad_row_addr=0x04;
	for(i=0;i<2000;i++);
	value=value|(*keypad_col_addr & 0x0f);
	*keypad_row_addr=0x00;
	switch(value){
	case 0x01: value=0x09; break;
	case 0x02: value=0x0a; break;
	case 0x04: value=0x0b; break;
	case 0x08: value=0x0c; break;
	}
	if(value!=0x00)goto stop_poll;

	*keypad_row_addr=0x08;
	for(i=0;i<2000;i++);
	value=value|(*keypad_col_addr & 0x0f);
	*keypad_row_addr=0x00;
	switch(value){
	case 0x01: value=0x0d; break;
	case 0x02: value=0x0e; break;
	case 0x04: value=0x0f; break;
	case 0x08: value=0x10; break;
	}
	if(value!=0x00)goto stop_poll;

	stop_poll:
	if(value>0){
	return value;
	}else{
	*keypad_row_addr=0x00;
	}

	}*/
}