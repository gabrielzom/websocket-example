
let stompClient = null
let button = document.querySelector('button')
let user = document.querySelector('input')
let text = document.querySelector('textarea')

button.addEventListener('click', connect, true)

function connect(evt) {
  if (user.value && user.value.trim().length) {
    user.disabled = true
    text.disabled = false
    button.removeEventListener('click', connect, true)
    button.addEventListener('click', sendMessage, true)
    button.innerText = 'Send Message'
    const sock = new SockJS('/ws')
    stompClient = Stomp.over(sock)
    stompClient.connect({}, onConnected, onError)
    evt.preventDefault()
  }
}

function onConnected() {
  stompClient.subscribe('/topic/public', onMessageReceived)
  stompClient.send(
    '/app/chat/user',
    {},
    JSON.stringify({ sender: user.value, type: 'JOIN'})
  )

}

function onError() {
  alert('An error has occurred. Please refresh page and try again. ')
}

function onMessageReceived(payload) {
  showMessageOnChat(JSON.parse(payload.body), true)
}

function sendMessage(evt) {
  if (text && text.value && stompClient) {
    const kerpMsg = {
      sender: user.value,
      text: text.value,
      type: 'CHAT',
    }
    stompClient.send(
      '/app/chat/send',
      {},
      JSON.stringify(kerpMsg)
    )
    showMessageOnChat(kerpMsg)
    text.value = ''
  }
  evt.preventDefault()
}

function showMessageOnChat(kerpMsg, received = false) {
  if (kerpMsg.sender === user.value && received) {
    return
  }
  const chat = document.querySelector('#chat')
  const msgText = document.createElement('div')
  if (kerpMsg.type !== 'CHAT') {
    msgText.style.borderRadius = '5px'
    msgText.style.padding = '5px'
    msgText.style.margin = '5px'
    msgText.style.color = '#8f3c03'
    msgText.style.border = `1px solid #d0a78b`
    msgText.style.background = '#ffe9be'
    msgText.innerHTML = `<strong>${kerpMsg.sender}</strong> ${kerpMsg.type} on the chat!`
    msgText.style.alignSelf = 'center'
  } else {
    msgText.style.borderRadius = '5px'
    msgText.style.padding = '5px'
    msgText.style.margin = '5px'
    msgText.style.width = '200px'
    msgText.style.color = received ? '#052b09' : '#15608e'
    msgText.style.border = `1px solid ${received ? '#49be54' : '#81d0ff'}`
    msgText.style.background = received ? '#d0ffcc' : '#d7f0ff'
    msgText.innerHTML = received ? `<strong>${kerpMsg.sender}</strong>: ${kerpMsg.text}` : kerpMsg.text
    msgText.style.alignSelf = received ? 'start' : 'end'
  }
  chat.appendChild(msgText)
}
